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

public class RebalanceOptions implements org.apache.thrift7.TBase<RebalanceOptions, RebalanceOptions._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift7.protocol.TStruct STRUCT_DESC = new org.apache.thrift7.protocol.TStruct("RebalanceOptions");

  private static final org.apache.thrift7.protocol.TField WAIT_SECS_FIELD_DESC = new org.apache.thrift7.protocol.TField("wait_secs", org.apache.thrift7.protocol.TType.I32, (short)1);
  private static final org.apache.thrift7.protocol.TField NUM_WORKERS_FIELD_DESC = new org.apache.thrift7.protocol.TField("num_workers", org.apache.thrift7.protocol.TType.I32, (short)2);

  private int wait_secs; // required
  private int num_workers; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift7.TFieldIdEnum {
    WAIT_SECS((short)1, "wait_secs"),
    NUM_WORKERS((short)2, "num_workers");

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
        case 1: // WAIT_SECS
          return WAIT_SECS;
        case 2: // NUM_WORKERS
          return NUM_WORKERS;
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
  private static final int __WAIT_SECS_ISSET_ID = 0;
  private static final int __NUM_WORKERS_ISSET_ID = 1;
  private BitSet __isset_bit_vector = new BitSet(2);

  public static final Map<_Fields, org.apache.thrift7.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift7.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift7.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.WAIT_SECS, new org.apache.thrift7.meta_data.FieldMetaData("wait_secs", org.apache.thrift7.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift7.meta_data.FieldValueMetaData(org.apache.thrift7.protocol.TType.I32)));
    tmpMap.put(_Fields.NUM_WORKERS, new org.apache.thrift7.meta_data.FieldMetaData("num_workers", org.apache.thrift7.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift7.meta_data.FieldValueMetaData(org.apache.thrift7.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift7.meta_data.FieldMetaData.addStructMetaDataMap(RebalanceOptions.class, metaDataMap);
  }

  public RebalanceOptions() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public RebalanceOptions(RebalanceOptions other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    this.wait_secs = other.wait_secs;
    this.num_workers = other.num_workers;
  }

  public RebalanceOptions deepCopy() {
    return new RebalanceOptions(this);
  }

  @Override
  public void clear() {
    set_wait_secs_isSet(false);
    this.wait_secs = 0;
    set_num_workers_isSet(false);
    this.num_workers = 0;
  }

  public int get_wait_secs() {
    return this.wait_secs;
  }

  public void set_wait_secs(int wait_secs) {
    this.wait_secs = wait_secs;
    set_wait_secs_isSet(true);
  }

  public void unset_wait_secs() {
    __isset_bit_vector.clear(__WAIT_SECS_ISSET_ID);
  }

  /** Returns true if field wait_secs is set (has been assigned a value) and false otherwise */
  public boolean is_set_wait_secs() {
    return __isset_bit_vector.get(__WAIT_SECS_ISSET_ID);
  }

  public void set_wait_secs_isSet(boolean value) {
    __isset_bit_vector.set(__WAIT_SECS_ISSET_ID, value);
  }

  public int get_num_workers() {
    return this.num_workers;
  }

  public void set_num_workers(int num_workers) {
    this.num_workers = num_workers;
    set_num_workers_isSet(true);
  }

  public void unset_num_workers() {
    __isset_bit_vector.clear(__NUM_WORKERS_ISSET_ID);
  }

  /** Returns true if field num_workers is set (has been assigned a value) and false otherwise */
  public boolean is_set_num_workers() {
    return __isset_bit_vector.get(__NUM_WORKERS_ISSET_ID);
  }

  public void set_num_workers_isSet(boolean value) {
    __isset_bit_vector.set(__NUM_WORKERS_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case WAIT_SECS:
      if (value == null) {
        unset_wait_secs();
      } else {
        set_wait_secs((Integer)value);
      }
      break;

    case NUM_WORKERS:
      if (value == null) {
        unset_num_workers();
      } else {
        set_num_workers((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case WAIT_SECS:
      return Integer.valueOf(get_wait_secs());

    case NUM_WORKERS:
      return Integer.valueOf(get_num_workers());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case WAIT_SECS:
      return is_set_wait_secs();
    case NUM_WORKERS:
      return is_set_num_workers();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof RebalanceOptions)
      return this.equals((RebalanceOptions)that);
    return false;
  }

  public boolean equals(RebalanceOptions that) {
    if (that == null)
      return false;

    boolean this_present_wait_secs = true && this.is_set_wait_secs();
    boolean that_present_wait_secs = true && that.is_set_wait_secs();
    if (this_present_wait_secs || that_present_wait_secs) {
      if (!(this_present_wait_secs && that_present_wait_secs))
        return false;
      if (this.wait_secs != that.wait_secs)
        return false;
    }

    boolean this_present_num_workers = true && this.is_set_num_workers();
    boolean that_present_num_workers = true && that.is_set_num_workers();
    if (this_present_num_workers || that_present_num_workers) {
      if (!(this_present_num_workers && that_present_num_workers))
        return false;
      if (this.num_workers != that.num_workers)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();

    boolean present_wait_secs = true && (is_set_wait_secs());
    builder.append(present_wait_secs);
    if (present_wait_secs)
      builder.append(wait_secs);

    boolean present_num_workers = true && (is_set_num_workers());
    builder.append(present_num_workers);
    if (present_num_workers)
      builder.append(num_workers);

    return builder.toHashCode();
  }

  public int compareTo(RebalanceOptions other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    RebalanceOptions typedOther = (RebalanceOptions)other;

    lastComparison = Boolean.valueOf(is_set_wait_secs()).compareTo(typedOther.is_set_wait_secs());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_wait_secs()) {
      lastComparison = org.apache.thrift7.TBaseHelper.compareTo(this.wait_secs, typedOther.wait_secs);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(is_set_num_workers()).compareTo(typedOther.is_set_num_workers());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_num_workers()) {
      lastComparison = org.apache.thrift7.TBaseHelper.compareTo(this.num_workers, typedOther.num_workers);
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
        case 1: // WAIT_SECS
          if (field.type == org.apache.thrift7.protocol.TType.I32) {
            this.wait_secs = iprot.readI32();
            set_wait_secs_isSet(true);
          } else { 
            org.apache.thrift7.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // NUM_WORKERS
          if (field.type == org.apache.thrift7.protocol.TType.I32) {
            this.num_workers = iprot.readI32();
            set_num_workers_isSet(true);
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
    if (is_set_wait_secs()) {
      oprot.writeFieldBegin(WAIT_SECS_FIELD_DESC);
      oprot.writeI32(this.wait_secs);
      oprot.writeFieldEnd();
    }
    if (is_set_num_workers()) {
      oprot.writeFieldBegin(NUM_WORKERS_FIELD_DESC);
      oprot.writeI32(this.num_workers);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("RebalanceOptions(");
    boolean first = true;

    if (is_set_wait_secs()) {
      sb.append("wait_secs:");
      sb.append(this.wait_secs);
      first = false;
    }
    if (is_set_num_workers()) {
      if (!first) sb.append(", ");
      sb.append("num_workers:");
      sb.append(this.num_workers);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift7.TException {
    // check for required fields
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bit_vector = new BitSet(1);
      read(new org.apache.thrift7.protocol.TCompactProtocol(new org.apache.thrift7.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift7.TException te) {
      throw new java.io.IOException(te);
    }
  }

}

