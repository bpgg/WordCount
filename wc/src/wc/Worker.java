package wc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Worker {
	
	//基本功能对应的属性
	int File_Lines;
	int File_Words;
	int File_Chars;
	
	//拓展功能对应的属性
	int File_Note_Lines;
	int File_Blank_Lines;
	int File_Code_Lines;
	
	String File_type;
	
	
	
	public String getFile_type() {
		return File_type;
	}
	public void setFile_type(String file_type) {
		File_type = file_type;
	}
	public int getFile_Lines() {
		return File_Lines;
	}
	public void setFile_Lines(int file_Lines) {
		File_Lines = file_Lines;
	}
	public int getFile_Words() {
		return File_Words;
	}
	public void setFile_Words(int file_Words) {
		File_Words = file_Words;
	}
	public int getFile_Chars() {
		return File_Chars;
	}
	public void setFile_Chars(int file_Chars) {
		File_Chars = file_Chars;
	}
	public int getFile_Note_Lines() {
		return File_Note_Lines;
	}
	public void setFile_Note_Lines(int file_Note_Lines) {
		File_Note_Lines = file_Note_Lines;
	}
	public int getFile_Blank_Lines() {
		return File_Blank_Lines;
	}
	public void setFile_Blank_Lines(int file_Blank_Lines) {
		File_Blank_Lines = file_Blank_Lines;
	}
	public int getFile_Code_Lines() {
		return File_Code_Lines;
	}
	public void setFile_Code_Lines(int file_Code_Lines) {
		File_Code_Lines = file_Code_Lines;
	}
	//获取处理文件对象
	public static BufferedReader FileWorker(String path) throws FileNotFoundException {
		 File file = new File(path);//定义一个file对象，用来初始化FileReader
	     FileReader reader = new FileReader(file);//定义一个fileReader对象，用来初始化BufferedReader
	     BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
	     
		 return bReader;
	}
	//获取文件行数
	public void lineNum(String path) throws IOException {
		
		BufferedReader reader=FileWorker(path);
		int count=0;
		while(reader.readLine()!=null) {
			count++;
		}
		setFile_Lines(count);
		
		reader.close();
	}
	
	//获取单词数目
	/*
	 * 我在这里对单词的定义是只包含连续的英文字母
	 * 在这里没有考虑单词跨行的情况
	 * */
	public void wordNum(String path) throws IOException {
		BufferedReader reader=FileWorker(path);
		int count=0;
		final String REGEX ="\\b[a-zA-Z]+\\b";
		String currentLine;
		Pattern p = Pattern.compile(REGEX);
		Matcher m;
		while((currentLine=reader.readLine())!=null) {
			currentLine=currentLine.trim();
			 m= p.matcher(currentLine);
			 while(m.find()) {
				 count++;
			 }
		}
		setFile_Words(count);
		System.out.println("单词数："+getFile_Words());
	}
	//获取字符数
	/**
	 * 我在这里对字符的定义是包括空白字符的，所以直接使用每次读取的行字符串的长度进行累加。
	 * */
	public void charNum(String path) throws IOException {
		BufferedReader reader=FileWorker(path);
		int count=0;
		String currentLine;
		while((currentLine=reader.readLine())!=null) {
			count+=currentLine.length();
		}
		System.out.println("字符数："+count);
		reader.close();
	}
	/*
	 * 空白行数
	 * 空行：本行全部是空格或格式控制字符，如果包括代码，
	 * 则只有不超过一个可显示的字符，例如“{”。
	 */
	public void blankLineNum(String path) throws IOException {
		BufferedReader reader=FileWorker(path);
		int count=0;
		String currentLine;
		
		/**
		 * 原使用将读取到的没行字符串去除空白字符后判断剩余字符串长度来决定是否为空行。
		 * 根据剩余长度为0，1，其他长度判断是否为空行
		 * 但是由于代码不够简洁，故使用正则表达式。、
		 * 但是具体两个方法的性能没有进行比较。
		 * 
		 * */
		//使用正则表达式
		String blankLineRegex = "(\\s*)([{};])(\\s*)";
		while((currentLine=reader.readLine())!=null) {
			if(currentLine.matches(blankLineRegex)) {
				count++;
			}
		}
		setFile_Blank_Lines(count);
		System.out.println("空白行数："+count);
		reader.close();
	}
	

	public void noteLineNum(String path) throws IOException {
		BufferedReader reader=FileWorker(path);
		int count=0;
		String currentLine;
		String Single_Line_Note_REGEX="(\\s*)([{};]?)(\\s*)(//)(.*)";
		String Block_Note_Start_REGEX="(\\s*)([{};]?)(\\s*)(/{1})(\\*{1})(.*)";
		String Block_Note_End_REGEX="(.*)(\\*{1})(/{1})(\\s*)";
		while((currentLine=reader.readLine())!=null) {
			if(currentLine.matches(Single_Line_Note_REGEX)) {
				count++;
			}else if(currentLine.matches(Block_Note_Start_REGEX)) {
				count++;
				while((currentLine=reader.readLine())!=null) {
					if(currentLine.matches(Block_Note_End_REGEX)) {
						count++;
						break;
					}else {
						count++;
					}
				}
			}
		}
		setFile_Note_Lines(count);
		System.out.println("注释行数："+count);
	}
	
	public void codeLineNum(String path) throws IOException {
		BufferedReader reader=FileWorker(path);
		lineNum(path);
		int count=getFile_Lines()-getFile_Blank_Lines()-getFile_Note_Lines();
		System.out.println("代码行数："+count);
	}

	
	public class myFileFilter implements FileFilter {  

	    public boolean accept(File file) {  

	        if(file.isDirectory())  
	            return true;  
	        else {  
	            String name = file.getName();  
	            if(name.endsWith(getFile_type())) {	            	
	            	return true;  
	            }  
	            else{	            	
	            	return false;  
	            } 
	        }  
	    }  
	  
	}  
	public void getAllFilePath(String rootPath)  {      
        File file = new File(rootPath);  
        File[] files = file.listFiles(new myFileFilter());  
        for(int i=0;i<files.length;i++)  {  
            if(files[i].isDirectory())   {  
                getAllFilePath(files[i].getPath());  
            }  
            else {  
                System.out.println(files[i].getPath());  
            }  
        }        
    }  
}
	

