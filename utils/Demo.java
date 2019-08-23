package club.leyvan.utils;

import java.util.regex.Pattern;

public class Demo {
	public static void main(String[] args){
		String name = "13665504672";
		String regex = "^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\\d{8})$";
		System.out.println(Pattern.matches(regex, name));
	}
}
