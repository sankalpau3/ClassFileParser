import java.io.*;

/**
 * Parses and displays a Java .class file.
 *
 * @author David Cooper
 */
public class ClassFileParser
{
    public static void main(String[] args)
    {
        if(args.length == 2)
        {
            try
            {
            	if(args[0].equals("-c")||args[0].equals("-m"))
            	{
            		String fileNames = getfilenames(args[1]);
            		String[] fileArray = fileNames.split(",");
            		for(String a:fileArray)
            		{
            			ClassFile cf = new ClassFile(a, args[0]);
                		System.out.println(cf);
            		}
            		
            	}
            	else
            	{
            		System.out.println("Invalid tag. please use \n -m for methods details \n -c to get method call \n please use only one tag at a time");
            	}
            }
            catch(IOException e)
            {
                System.out.printf("Cannot read \"%s\": %s\n",
                    args[1], e.getMessage());
            }
            catch(ClassFileParserException e)
            {
                System.out.printf("Class file format error in \"%s\": %s\n",
                    args[1], e.getMessage());
            }
        }
        else
        {
            System.out.println("Usage: java ClassFileParser <tag> <class-file or direcotry name>");
        }
    }
    
    /**
     * This method acept a file name or a directory in string as input parameter
     * This returns a string the class file name or the names seperated by commas
     */
    public static String getfilenames(String names)
    {
    	String nameSet="";
    	String fname;
    	if((names.substring(names.lastIndexOf('.')+1).equals("class")))// if a name of a class file is passed as command line input
    	{
    		nameSet = ","+names;
    	}
    	else//if a directory name is passed as comman line input
    	{
    		File folder = new File(names);
    		File[] listOfFiles = folder.listFiles();

    		    for (int i = 0; i < listOfFiles.length; i++) {
    		      if (listOfFiles[i].isFile()) {
    		    		  fname = listOfFiles[i].toString();
    		    		  if((fname.substring(fname.lastIndexOf('.')+1).equals("class")))
    		    		  {
    		    			  nameSet = nameSet+","+fname;
    		    			  i++;
    		    		  }
    		    	  }
    		       
    		    }
    	}
    			
    	return nameSet.substring(1);
    }
}
