package backtype.storm.tuple;

import java.util.List;

public interface ITuple {

    /**
     * Returns the number of fields in this tuple.
     */
    public int size();

    /**
     * Returns true if this tuple contains the specified name of the field.
     */
    public boolean contains(String field);

    /**
     * Gets the names of the fields in this tuple.
     */
    public Fields getFields();

    /**
     * Returns the position of the specified field in this tuple.
     */
    public int fieldIndex(String field);

    /**
     * Returns a subset of the tuple based on the fields selector.
     */
    public List<Object> select(Fields selector);

    /**
     * Gets the field at position i in the tuple. Returns object since tuples are dynamically typed.
     */
    public Object getValue(int i);

    /**
     * Returns the String at position i in the tuple. If that field is not a String,
     * you will get a runtime error.
     */
    public String getString(int i);

    /**
     * Returns the Integer at position i in the tuple. If that field is not an Integer,
     * you will get a runtime error.
     */
    public Integer getInteger(int i);

    /**
     * Returns the Long at position i in the tuple. If that field is not a Long,
     * you will get a runtime error.
     */
    public Long getLong(int i);

    /**
     * Returns the Boolean at position i in the tuple. If that field is not a Boolean,
     * you will get a runtime error.
     */
    public Boolean getBoolean(int i);

    /**
     * Returns the Short at position i in the tuple. If that field is not a Short,
     * you will get a runtime error.
     */
    public Short getShort(int i);

    /**
     * Returns the Byte at position i in the tuple. If that field is not a Byte,
     * you will get a runtime error.
     */
    public Byte getByte(int i);

    /**
     * Returns the Double at position i in the tuple. If that field is not a Double,
     * you will get a runtime error.
     */
    public Double getDouble(int i);

    /**
     * Returns the Float at position i in the tuple. If that field is not a Float,
     * you will get a runtime error.
     */
    public Float getFloat(int i);

    /**
     * Returns the byte array at position i in the tuple. If that field is not a byte array,
     * you will get a runtime error.
     */
    public byte[] getBinary(int i);


    public Object getValueByField(String field);

    public String getStringByField(String field);

    public Integer getIntegerByField(String field);

    public Long getLongByField(String field);

    public Boolean getBooleanByField(String field);

    public Short getShortByField(String field);

    public Byte getByteByField(String field);

    public Double getDoubleByField(String field);

    public Float getFloatByField(String field);

    public byte[] getBinaryByField(String field);

    /**
     * Gets all the values in this tuple.
     */
    public List<Object> getValues();



}
