package backtype.storm.transactional.state;

import backtype.storm.transactional.TransactionalSpoutCoordinator;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * A map from txid to a value. Automatically deletes txids that have been
 * committed.
 */
public class RotatingTransactionalState {
	public static interface StateInitializer {
		Object init(BigInteger txid, Object lastState);
	}

	private TransactionalState _state;
	private String _subdir;
	private boolean _strictOrder;

	private TreeMap<BigInteger, Object> _curr = new TreeMap<BigInteger, Object>();

	public RotatingTransactionalState(TransactionalState state, String subdir,
			boolean strictOrder) {
		_state = state;
		_subdir = subdir;
		_strictOrder = strictOrder;
		state.mkdir(subdir);
		sync();
	}

	public RotatingTransactionalState(TransactionalState state, String subdir) {
		this(state, subdir, false);
	}

	public Object getLastState() {
		if (_curr.isEmpty())
			return null;
		else
			return _curr.lastEntry().getValue();
	}

	public void overrideState(BigInteger txid, Object state) {
		_state.setData(txPath(txid), state);
		_curr.put(txid, state);
	}

	public void removeState(BigInteger txid) {
		if (_curr.containsKey(txid)) {
			_curr.remove(txid);
			_state.delete(txPath(txid));
		}
	}

	public Object getState(BigInteger txid, StateInitializer init) {
		if (!_curr.containsKey(txid)) {
			SortedMap<BigInteger, Object> prevMap = _curr.headMap(txid);
			SortedMap<BigInteger, Object> afterMap = _curr.tailMap(txid);

			BigInteger prev = null;
			if (!prevMap.isEmpty())
				prev = prevMap.lastKey();

			if (_strictOrder) {
				if (prev == null
						&& !txid.equals(TransactionalSpoutCoordinator.INIT_TXID)) {
					throw new IllegalStateException(
							"Trying to initialize transaction for which there should be a previous state");
				}
				if (prev != null && !prev.equals(txid.subtract(BigInteger.ONE))) {
					throw new IllegalStateException(
							"Expecting previous txid state to be the previous transaction");
				}
				if (!afterMap.isEmpty()) {
					throw new IllegalStateException(
							"Expecting tx state to be initialized in strict order but there are txids after that have state");
				}
			}

			Object data;
			if (afterMap.isEmpty()) {
				Object prevData;
				if (prev != null) {
					prevData = _curr.get(prev);
				} else {
					prevData = null;
				}
				data = init.init(txid, prevData);
			} else {
				data = null;
			}
			_curr.put(txid, data);
			_state.setData(txPath(txid), data);
		}
		return _curr.get(txid);
	}

	public boolean hasCache(BigInteger txid) {
		return _curr.containsKey(txid);
	}

	/**
	 * Returns null if it was created, the value otherwise.
	 */
	public Object getStateOrCreate(BigInteger txid, StateInitializer init) {
		if (_curr.containsKey(txid)) {
			return _curr.get(txid);
		} else {
			getState(txid, init);
			return null;
		}
	}

	public void cleanupBefore(BigInteger txid) {
		Set<BigInteger> toDelete = new HashSet<BigInteger>();
		toDelete.addAll(_curr.headMap(txid).keySet());
		for (BigInteger tx : toDelete) {
			_curr.remove(tx);
			_state.delete(txPath(tx));
		}
	}

	private void sync() {
		List<String> txids = _state.list(_subdir);
		for (String txid_s : txids) {
			Object data = _state.getData(txPath(txid_s));
			_curr.put(new BigInteger(txid_s), data);
		}
	}

	private String txPath(BigInteger tx) {
		return txPath(tx.toString());
	}

	private String txPath(String tx) {
		return _subdir + "/" + tx;
	}

}
