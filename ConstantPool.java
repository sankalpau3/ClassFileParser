import java.io.*;

/**
 * Parses and stores the constant pool from a Java .class file.
 *
 * @author David Cooper
 */
public class ConstantPool
{
    private CPEntry[] entries;
    private int cpSize;

    /**
     * Parses the constant pool, including the length, constructing a
     * ConstantPool object in the process.
     */
    public ConstantPool(DataInputStream dis) throws InvalidTagException,
                                                    InvalidConstantPoolIndex,
                                                    IOException
    {
        int len = dis.readUnsignedShort();
        entries = new CPEntry[len];
        this.cpSize = len;
        int i;

        // Initialise entries to null.
        for(i = 0; i < len; i++)
        {
            entries[i] = null;
        }
        
        i = 1;
        while(i < len)
        {
            entries[i] = CPEntry.parse(dis);

            // We can't just have i++, because certain entries (Long and
            // Double) count for two entries.
            i += entries[i].getEntryCount();
        }
       // System.out.println(dis.readUnsignedShort()+" This is the unassigntd short");
        // Once the constant pool has been parsed, resolve the various
        // internal references.
        for(i = 0; i < len; i++)
        {
            if(entries[i] != null)
            {
                entries[i].resolveReferences(this);
            }
        }
    }

    /** Retrieves a given constant pool entry. */
    public CPEntry getEntry(int index) throws InvalidConstantPoolIndex
    {
        if(index < 0 || index > entries.length)
        {
            throw new InvalidConstantPoolIndex(String.format(
                "Invalid constant pool index: %d (not in range [0, %d])",
                index, entries.length));
        }
        else if(entries[index] == null)
        {
            throw new InvalidConstantPoolIndex(String.format(
                "Invalid constant pool index: %d (entry undefined)", index));
        }
        return entries[index];
    }
    
    

    /** Returns a formatted String representation of the constant pool. */
    public String toString()
    {
    	String sa = "constan pool is in the text file named cpool.txt";
        String s = "Index  Entry type          Entry values\n" +
                   "---------------------------------------\n";
        for(int i = 0; i < entries.length; i++)
        {
            if(entries[i] != null)
            {
                s += String.format("0x%02x   %-18s  %s\n",
                    i, entries[i].getTagString(), entries[i].getValues());
            }
        }
        return sa;
    }
    //this method will return the size of the constant pool.
    //this will help to get the byte count to skip.
    public int getCpSize()
    {
    	return this.cpSize;
    }
    
    public CPEntry getEntryByIndexVal(String index) throws InvalidConstantPoolIndex
    {    	
    	for(int i = 0; i < entries.length; i++)
        {
    		
        	if(index.equalsIgnoreCase(String.format("0x%02x",i)))
        	{
        		return entries[i];
        	}
        }
        return null;
    }
    
}

/**
 * Thrown when an invalid index into the constant pool is given. That is,
 * index is zero (or negative), greater than the index of the last entry, or
 * represents the (unused) entry following a Long or Double.
 */
class InvalidConstantPoolIndex extends ClassFileParserException
{
    public InvalidConstantPoolIndex(String msg) { super(msg); }
}
