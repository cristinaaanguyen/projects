
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ParseLog {
	
	
	
	
    public static void main( String[] args)
    {
        long TStotal = 0;
        long TJtotal = 0;
        int count = 26420;
        long TSavg = 0;
        long TJavg = 0;
	    	try {
				File file = new File("/home/ubuntu/project5logSingleInstance1_4.txt");
				FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				//StringBuffer stringBuffer = new StringBuffer();
				String line;
				while ((line = bufferedReader.readLine()) != null) {
				//	stringBuffer.append(line);
				//	stringBuffer.append("\n");
					String [] content = line.split(" ");
					TStotal += Long.parseLong(content[0]);
					TJtotal += Long.parseLong(content[1]);
					
				}
				fileReader.close();
				TSavg = (long)((float)TStotal/count);
				TJavg = (long)((float)TJtotal/count);
				System.out.println("TSavg: " + TSavg);
				System.out.println("TJavg: " + TJavg);

				//System.out.println(stringBuffer.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}

    }

}
