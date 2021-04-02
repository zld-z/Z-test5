import java.text.Collator;
import java.util.*; 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Province {
	static int j =0;
	static String location;
	static int count=0;
	static String loc;
	static int sum=0;
    static String[][] string;
    //对省份的城市进行分割
	static String[][] rank=new String[9][3];
	//进行省份的排序
	static String[][] pro =new String[9][2];
	static Map<Object,Object> dic =new HashMap<Object,Object>();
	static Comparator<Object> com=Collator.getInstance(java.util.Locale.CHINA);
	//创建一个列表,用来装按行读取到的内容
	static List<String> list=new ArrayList<>();
	
	public static void readFileByLines(String filename) throws IOException {
		File file = new File(filename);
		BufferedReader reader=null;
		InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), "GBK");
		reader = new BufferedReader(inputStreamReader);
		String tempString;
		while((tempString = reader.readLine())!= null)
		{
				list.add(tempString);
		}
		inputStreamReader.close();
		reader.close();
		string = new String[list.size()][];
		for(int i = 0;i<list.size();i++) 
		{
			string[i] = list.get(i).split("\\s+");
		}
	}	
	public static void change(String[][] str) {
		String[][] exc = new String[str.length][];
		for(int j=0;j<str.length;j++)
		{
			for(int k=j+1;k<str.length;k++)
			{
				if(Integer.valueOf(str[j][1])>Integer.valueOf(str[k][1]))
				{
					continue;
				}
				else if(Integer.valueOf(str[j][1])<Integer.valueOf(str[k][1]))
				{
					exc[j]=str[k];
					str[k]=str[j];
					str[j]=exc[j];
				}
				else 
				{
					if(com.compare(str[k][0],str[j][0])>=0) 
					{
						continue;
					}
					else 
					{
						exc[j]=str[k];
						str[k]=str[j];
						str[j]=exc[j];
					}
				}
			}
		}
	}

	//按照省份对省份内的城市进行排序
	//a为一个省份的起始位置，b为一个省份的结束位置
	public static void exchange(String[][] str,int a,int b) {
		String[][] exc = new String[str.length][];
		for(int j=a;j<b-1;j++)
		{
			for(int k=j+1;k<b-1;k++) 
			{
				if(Integer.valueOf(str[j][2])>Integer.valueOf(str[k][2]))
				{
					continue;
				}
				else if(Integer.valueOf(str[j][2])<Integer.valueOf(str[k][2]))
				{
					exc[j]=str[k];
					str[k]=str[j];
					str[j]=exc[j];
				}
				else {
					if(com.compare(str[k][1],str[j][1])>=0)
					{
						continue;
					}
					else 
					{
					exc[j]=str[k];
					str[k]=str[j];
					str[j]=exc[j];
					}
				}
			}
		}	
	}

	public static void rank(String[][] str) {
		String s= str[0][0];
		int count=0;
		//设定第一个起始位置为0
		rank[count][1]=String.valueOf(0);
		for(int b=0;b<str.length;b++) 
		{
			if(str[b][0].equals(s)) 
			{
				continue;
			}
			else 
			{
				rank[count][0]=str[b-1][0];
				rank[count][2]=String.valueOf(b-1);
				count=count+1;
				rank[count][1]=String.valueOf(b);
				s=str[b][0];
			}
		}
		rank[count][0]=str[str.length-1][0];
		rank[count][2]=String.valueOf(str.length-1);
	}
	
	public static void pro()
	{
		loc=string[0][0];
		for(int r=0;r<string.length;r++) 
		{
			if(string[r][0].equals(loc))
			{
				sum+=Integer.valueOf(string[r][2]);
			}
			else 
			{
				dic.put(string[r-1][0], sum);
				sum=Integer.valueOf(string[r][2]);
				loc=string[r][0];
			}
		}
		dic.put(string[string.length-1][0], sum);
		for(Object key :dic.keySet()) 
		{
			pro[count][0]=key.toString();
			pro[count][1]=dic.get(key).toString();
			count++;
		}
	}

	public static void area() {
		location=pro[0][0];
		for(int j =0 ;j < pro.length;j++) 
		{
			for(int i=0;i<rank.length;i++) 
			{
				if(rank[i][0].equals(pro[j][0]))
				{
				exchange(string,Integer.valueOf(rank[i][1]),Integer.valueOf(rank[i][2]));
				}
				else 
				{
					continue;
				}
			}
		}
	}
	public static void main(String[] args) throws IOException {
		readFileByLines(args[0]);
		pro();
		change(pro);
		System.out.println("按总人数从多到少的排序方法进行省份的排序：");
		for(String[] s :pro)
		{
			for(String r :s) 
			{
				System.out.println(r+"  ");
			}
		}
		rank(string);
		System.out.println("各省份及其所包含城市的范围：");
		for(String[] s :rank) 
		{
			for(String r :s) 
			{
				System.out.print(r+"       ");
			}
		}
		area();
		File file = new File(args[1]);
		FileOutputStream out = new FileOutputStream(file);
		OutputStreamWriter wr =new OutputStreamWriter(out);
		if(args.length<2||args.length>3) 
		{
			System.out.println("参数出错");
		}
		else if(args.length==2)
		{
			//先写入人数最多的省份并加换行到文档
			wr.write(location+"            "+pro[0][1]+"\r\n\r\n");
			for(int j = 0;j < pro.length;j++)
			{
				for(int i=0;i<string.length;i++) 
				{
					if(string[i][0].equals(pro[j][0]))
					{
						wr.write(string[i][1]+"            "+string[i][2]+"\r\n");	
					}
					else 
					{
						continue;
					}
				}
				if(j!=pro.length-1) 
				{
					location=pro[j+1][0];
					wr.write(location+"            "+pro[j+1][1]+"\r\n\r\n");
				}
				else
			    break;
			}	
		}
		else 
		{
			location = args[2];
			wr.write(location+"            "+pro[0][1]+"\r\n\r\n");
			for(int j =0 ;j < string.length;j++)
			{
				if(string[j][0].equals(location))
				{
					wr.write(string[j][1]+"            "+string[j][2]+"\r\n");
				}
				else
				{
					continue;
				}
			}
		}
		wr.close();
	}
}