package wc;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public Main() {
	}
	public static void main(String[] args) throws IOException {
		Worker worker = new Worker();
		//要处理的文件目录
		String path="D:\\test.txt";
		String str = null;
		String[] a = null;
		while(true) {
			Scanner sc = new Scanner(System.in);
			str=sc.nextLine();
			str=str.trim();
		    a = str.split("\\s");
		    if(a[0].equals("-end")) {
		    	System.out.println("欢迎下次使用程序");
		    	sc.close();
		    	break;
		    }else if(a[0].equals("-s")) {
				worker.setFile_type(a[2]);
				worker.getAllFilePath(a[1]);
			}else if(a[0].equals("-c")){
				worker.charNum(a[1]);
			}else if(a[0].equals("-w")){
				worker.wordNum(a[1]);
			}else if(a[0].equals("-l")){
				worker.lineNum(a[1]);
				System.out.println("文件行数："+worker.getFile_Lines());
			}else if(a[0].equals("-a")){
				worker.noteLineNum(a[1]);
				worker.blankLineNum(a[1]);
				worker.codeLineNum(a[1]);
				worker.getFile_Code_Lines();
				worker.getFile_Note_Lines();
				worker.getFile_Blank_Lines();
			}
		}
	}
}
