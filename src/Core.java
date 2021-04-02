import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Core {
	public static void main(String[] args) throws IOException {
		if(args.length==2) 
		{
			tip2(args[0],args[1]);
		}
		else if(args.length==3)
		{
			tip3(args[0],args[1],args[2]);
		}
		else 
		{
			System.out.println("参数输入有误,请重新输入!");
		}
	}
	public static void tip2(String inputfile,String outputfile) throws IOException {
		Province.readFileByLines(inputfile);
		Province.pro();
		Province.change(Province.pro);
		Province.rank(Province.string);
		System.out.println("各省份及其所包含城市的范围：");
		Province.area();
		File file = new File(outputfile);
		FileOutputStream out = new FileOutputStream(file);
		OutputStreamWriter wr =new OutputStreamWriter(out);		
		wr.write(Province.location+"            "+Province.pro[0][1]+"\r\n\r\n");
		for(int m = 0;m < Province.pro.length;m++) 
		{
				for(int i=0;i<Province.string.length;i++) 
				{
					if(Province.string[i][0].equals(Province.pro[m][0]))
					{
						wr.write(Province.string[i][1]+"            "+Province.string[i][2]+"\r\n");	
					}
					else
					{
						continue;
					}
				}
				if(m!=Province.pro.length-1) 
				{
					Province.location=Province.pro[m+1][0];
					wr.write(Province.location+"            "+Province.pro[m+1][1]+"\r\n\r\n");
				}
				else
				break;
			}	
			wr.close();
	   }
	
	public static void tip3(String inputfile,String outputfile,String area) throws IOException  {
		Province.readFileByLines(inputfile);
		Province.pro();
		Province.change(Province.pro);
		Province.rank(Province.string);
		System.out.println("各省份及其所包含城市的范围：");
		Province.area();
		File file = new File(outputfile);
		FileOutputStream out = new FileOutputStream(file);
		OutputStreamWriter wr =new OutputStreamWriter(out);
		Province.location=area;
		wr.write(Province.location+"            "+Province.pro[0][1]+"\r\n\r\n");
		for(int n =0 ;n < Province.string.length;n++)
		{
			if(Province.string[n][0].equals(Province.location))
			{
				wr.write(Province.string[n][1]+"            "+Province.string[n][2]+"\r\n");
			}
			else
			{
				continue;
			}
		}
		wr.close();
	}
}