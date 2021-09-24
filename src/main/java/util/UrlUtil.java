package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;

public class UrlUtil {
	
	private final Logger log = Logger.getLogger(this.getClass());
	
	private String readAll(Reader rd) throws Exception{
		
		log.info(this.getClass().getName() + "readAll Start!");
		
		StringBuilder sb = null;
		
		try {
			sb = new StringBuilder();
			int cp = 0;
			
			while((cp = rd.read()) != -1) {
				sb.append((char)cp);
			}
		} catch (Exception e){
			log.info(this.getClass().getName() + "readAll Exception : " + e.toString());
		}
		
		log.info(this.getClass().getName() + "readAll End!");
		
		return sb.toString();
	}
	
	public String urlReadForString(String url) throws IOException {
		
		log.info(this.getClass().getName() + "urlReadForString Start!");
		log.info(this.getClass().getName() + " url : " + url);
		
		BufferedReader rd = null;
		InputStream is = null;
		
		String res = "";
		
		try {
			is = new URL(url).openStream();
			
			// URL 결과 내용이 많을 수 있기 때문에 BffuredReader를 통해 조금씩 가져오도록 설정
			rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			
			// 결과 가져오기
			res = readAll(rd);
			
		} catch(Exception e) {
			log.info(this.getClass().getName() + "urlReaderForString Exception : " + e.toString());
			
		} finally {
			
			is.close();
			is = null;
			rd = null;
			
		}
		
		log.info(this.getClass().getName() + "urlReadForString End!");
		
		return res;
	}
}
