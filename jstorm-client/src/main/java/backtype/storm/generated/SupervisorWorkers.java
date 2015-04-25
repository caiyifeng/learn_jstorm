/**
 * Autogenerated by Thrift Compiler (0.7.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package backtype.storm.generated;

import org.apache.commons.lang.builder.HashCodeBuilder;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SupervisorWorkers implements org.apache.thrift7.TBase<SupervisorWorkers, SupervisorWorkers._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift7.protocol.TStruct STRUCT_DESC = new org.apache.thrift7.protocol.TStruct("SupervisorWorkers");

  private static final org.apache.thrift7.protocol.TField SUPERVISOR_FIELD_DESC = new org.apache.thrift7.protocol.TField("supervisor", org.apache.thrift7.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift7.protocol.TField WORKERS_FIELD_DESC = new org.apache.thrift7.protocol.TField("workers", org.apache.thrift7.protocol.TType.LIST, (short)2);

  private SupervisorSummary supervisor; // required
  private List<WorkerSummary> workers; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift7.TFieldIdEnum {
    SUPERVISOR((short)1, "supervisor"),
    WORKERS((short)2, "workers");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // SUPERVISOR
          return SUPERVISOR;
        case 2: // WORKERS
          return WORKERS;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments

  public static final Map<_Fields, org.apache.thrift7.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift7.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift7.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SUPERVISOR, new org.apache.thrift7.meta_data.FieldMetaData("supervisor", org.apache.thrift7.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift7.meta_data.StructMetaData(org.apache.thrift7.protocol.TType.STRUCT, SupervisorSummary.class)));
    tmpMap.put(_Fields.WORKERS, new org.apache.thrift7.meta_data.FieldMetaData("workers", org.apache.thrift7.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift7.meta_data.ListMetaData(org.apache.thrift7.protocol.TType.LIST, 
            new org.apache.thrift7.meta_data.StructMetaData(org.apache.thrift7.protocol.TType.STRUCT, WorkerSummary.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift7.meta_data.FieldMetaData.addStructMetaDataMap(SupervisorWorkers.class, metaDataMap);
  }

  public SupervisorWorkers() {
  }

  public SupervisorWorkers(
    SupervisorSummary supervisor,
    List<WorkerSummary> workers)
  {
    this();
    this.supervisor = supervisor;
    this.workers = workers;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SupervisorWorkers(SupervisorWorkers other) {
    if (other.is_set_supervisor()) {
      this.supervisor = new SupervisorSummary(other.supervisor);
    }
    if (other.is_set_workers()) {
      List<WorkerSummary> __this__workers = new ArrayList<WorkerSummary>();
      for (WorkerSummary other_element : other.workers) {
        __this__workers.add(new WorkerSummary(other_element));
      }
      this.workers = __this__workers;
    }
  }

  public SupervisorWorkers deepCopy() {
    return new SupervisorWorkers(this);
  }

  @Override
  public void clear() {
    this.supervisor = null;
    this.workers = null;
  }

  public SupervisorSummary get_supervisor() {
    return this.supervisor;
  }

  public void set_supervisor(SupervisorSummary supervisor) {
    this.supervisor = supervisor;
  }

  public void unset_supervisor() {
    this.supervisor = null;
  }

  /** Returns true if field supervisor is set (has been assigned a value) and false otherwise */
  public boolean is_set_supervisor() {
    return this.supervisor != null;
  }

  public void set_supervisor_isSet(boolean value) {
    if (!value) {
      this.supervisor = null;
    }
  }

  public int get_workers_size() {
    return (this.workers == null) ? 0 : this.workers.size();
  }

  public java.util.Iterator<WorkerSummary> get_workers_iterator() {
    return (this.workers == null) ? null : this.workers.iterator();
  }

  public void add_to_workers(WorkerSummary elem) {
    if (this.workers == null) {
      this.workers = new ArrayList<WorkerSummary>();
    }
    this.workers.add(elem);
  }

  public List<WorkerSummary> get_workers() {
    return this.workers;
  }

  public void set_workers(List<WorkerSummary> workers) {
    this.workers = workers;
  }

  public void unset_workers() {
    this.workers = null;
  }

  /** Returns true if field workers is set (has been assigned a value) and false otherwise */
  public boolean is_set_workers() {
    return this.workers != null;
  }

  public void set_workers_isSet(boolean value) {
    if (!value) {
      this.workers = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SUPERVISOR:
      if (value == null) {
        unset_supervisor();
      } else {
        set_supervisor((SupervisorSummary)value);
      }
      break;

    case WORKERS:
      if (value == null) {
        unset_workers();
      } else {
        set_workers((List<WorkerSummary>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SUPERVISOR:
      return get_supervisor();

    case WORKERS:
      return get_workers();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SUPERVISOR:
      return is_set_supervisor();
    case WORKERS:
      return is_set_workers();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof SupervisorWorkers)
      return this.equals((SupervisorWorkers)that);
    return false;
  }

  public boolean equals(SupervisorWorkers that) {
    if (that == null)
      return false;

    boolean this_present_supervisor = true && this.is_set_supervisor();
    boolean that_present_supervisor = true && that.is_set_supervisor();
    if (this_present_supervisor || that_present_supervisor) {
      if (!(this_present_supervisor && that_present_supervisor))
        return false;
      if (!this.supervisor.equals(that.supervisor))
        return false;
    }

    boolean this_present_workers = true && this.is_set_workers();
    boolean that_present_workers = true && that.is_set_workers();
    if (this_present_workers || that_present_workers) {
      if (!(this_present_workers && that_present_workers))
        return false;
      if (!this.workers.equals(that.workers))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();

    boolean present_supervisor = true && (is_set_supervisor());
    builder.append(present_supervisor);
    if (present_supervisor)
      builder.append(supervisor);

    boolean present_workers = true && (is_set_workers());
    builder.append(present_workers);
    if (present_workers)
      builder.append(workers);

    return builder.toHashCode();
  }

  public int compareTo(SupervisorWorkers other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    SupervisorWorkers typedOther = (SupervisorWorkers)other;

    lastComparison = Boolean.valueOf(is_set_supervisor()).compareTo(typedOther.is_set_supervisor());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_supervisor()) {
      lastComparison = org.apache.thrift7.TBaseHelper.compareTo(this.supervisor, typedOther.supervisor);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(is_set_workers()).compareTo(typedOther.is_set_workers());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_workers()) {
      lastComparison = org.apache.thrift7.TBaseHelper.compareTo(this.workers, typedOther.workers);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift7.protocol.TProtocol iprot) throws org.apache.thrift7.TException {
    org.apache.thrift7.protocol.TField field;
    iprot.readStructBegin();
    while (true)
    {
      field = iprot.readFieldBegin();
      if (field.type == org.apache.thrift7.protocol.TType.STOP) { 
        break;
      }
      switch (field.id) {
        case 1: // SUPERVISOR
          if (field.type == org.apache.thrift7.protocol.TType.STRUCT) {
            this.supervisor = new SupervisorSummary();
            this.supervisor.read(iprot);
          } else { 
            org.apache.thrift7.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // WORKERS
          if (field.type == org.apache.thrift7.protocol.TType.LIST) {
            {
              org.apache.thrift7.protocol.TList _list205 = iprot.readListBegin();
              this.workers = new ArrayList<WorkerSummary>(_list205.size);
              for (int _i206 = 0; _i206 < _list205.size; ++_i206)
              {
                WorkerSummary _elem207; // required
                _elem207 = new WorkerSummary();
                _elem207.read(iprot);
                this.workers.add(_elem207);
              }
              iprot.readListEnd();
            }
          } else { 
            org.apache.thrift7.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        default:
          org.apache.thrift7.protocol.TProtocolUtil.skip(iprot, field.type);
      }
      iprot.readFieldEnd();
    }
    iprot.readStructEnd();
    validate();
  }

  public void write(org.apache.thrift7.protocol.TProtocol oprot) throws org.apache.thrift7.TException {
    validate();

    oprot.writeStructBegin(STRUCT_DESC);
    if (this.supervisor != null) {
      oprot.writeFieldBegin(SUPERVISOR_FIELD_DESC);
      this.supervisor.write(oprot);
      oprot.writeFieldEnd();
    }
    if (this.workers != null) {
      oprot.writeFieldBegin(WORKERS_FIELD_DESC);
      {
        oprot.writeListBegin(new org.apache.thrift7.protocol.TList(org.apache.thrift7.protocol.TType.STRUCT, this.workers.size()));
        for (WorkerSummary _iter208 : this.workers)
        {
          _iter208.write(oprot);
        }
        oprot.writeListEnd();
      }
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("SupervisorWorkers(");
    boolean first = true;

    sb.append("supervisor:");
    if (this.supervisor == null) {
      sb.append("null");
    } else {
      sb.append(this.supervisor);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("workers:");
    if (this.workers == null) {
      sb.append("null");
    } else {
      sb.append(this.workers);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift7.TException {
    // check for required fields
    if (!is_set_supervisor()) {
      throw new org.apache.thrift7.protocol.TProtocolException("Required field 'supervisor' is unset! Struct:" + toString());
    }

    if (!is_set_workers()) {
      throw new org.apache.thrift7.protocol.TProtocolException("Required field 'workers' is unset! Struct:" + toString());
    }

  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift7.protocol.TCompactProtocol(new org.apache.thrift7.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift7.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift7.protocol.TCompactProtocol(new org.apache.thrift7.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift7.TException te) {
      throw new java.io.IOException(te);
    }
  }

}

