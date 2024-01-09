package com.ppj.oct.item;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppj.oct.login.SqueezeUser;

@Service
public class IDAO {
	
	@Autowired
	private ServletContext ServletContext;
	
	
	@SuppressWarnings("unchecked")
	public ImageURLs getImages(HttpServletRequest req) throws Exception {//이미지 url 을 크롤링으로 가져와 json으로 return, 추출한 이미지 url목록을 session에 담기
		System.out.println("getImages");				
		HttpSession session = req.getSession();
		
		String url = isValidURL(req.getParameter("address"));
		System.out.println(url);
		
		if(url == null) {
			System.out.println("유효한 url입력이 아닙니다.");
			session.setAttribute("status", "error.JPG");
			return null;
		}else {
			System.out.println("유효한 url입력입니다. :"+ url);
			ImageURL iurl = new ImageURL();
			ImageURLs iurls = null;
			ArrayList<ImageURL> imgSrc = new ArrayList<ImageURL>();
			ArrayList<String> string_url = new ArrayList<String>();
			
			try {
				
				session.setAttribute("URL", url);
				
				Document doc = Jsoup.connect(url).get();
				Elements imgElements = doc.select("img");
				
				for(Element imgElement : imgElements) {
					
					String imgUrl = imgElement.attr("src");
					System.out.println(imgUrl);
					
					if(isValidURL(imgUrl,req)!=null) {
						imgUrl = isValidURL(imgUrl,req);
						System.out.println("valid:yes");
						iurl = new ImageURL(imgUrl);
						imgSrc.add(iurl);
						string_url.add(imgUrl);
					}
					
				}
				
				if(string_url.size() == 0) {//유효한 이미지 url을 하나도 얻지 못한경우
					session.setAttribute("status", "error.JPG");
				}
				session.setAttribute("string_url", string_url);
				
				imgToServer(req); //이미지 url을 통해 서버에 이미지를 저장, 세션에 ImageFileList로 저장된 이미지파일들의 이름 목록을 담는다.
				ArrayList<String> ImageFileList = (ArrayList<String>) req.getSession().getAttribute("ImageFileList");
				iurls = new ImageURLs(imgSrc,ImageFileList);
				return iurls;
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
			return iurls;
		}
	}//getImages end
	
	public String checkMIMEType(String s) { //이미지 url에서 이미지 확장자가 무엇인지 판별하여 리턴
		
		if(s.contains(".jpg")) {
			return ".jpg";
		}else if(s.contains(".png")) {
			return ".png";
		}else if(s.contains(".svg")) {
			return ".svg";
		}
		
		return ".jpg";
	}
	
	public String generateFilename(String mimeType) {//고유한(중복되지 않는)파일 이름 생성하기
		String uuid = UUID.randomUUID().toString();
		
		return uuid+mimeType;
	}
	
	@SuppressWarnings("unchecked")
	public void imgToServer(HttpServletRequest req) {//이미지 url 을 이용해 서버에 이미지저장
		
		ArrayList<String> string_url = (ArrayList<String>) req.getSession().getAttribute("string_url");
		int len = string_url.size();
		ArrayList<String> ImageFileList = checkListInSession("ImageFileList", req.getSession());
		
		if(len != 0) {
		    String directory = ServletContext.getRealPath("fileUpDown")+"/";
			InputStream in = null;

			try {
				for(int i=0; i<len; i++) {
					String s = string_url.get(i);
					URL url = new URL(s);
					in = url.openStream();
					String fileName = generateFilename(checkMIMEType(s));
					ImageFileList.add(fileName);
					Path destination = new File(directory+fileName).toPath();
					Files.copy(in, destination, StandardCopyOption.REPLACE_EXISTING);
					in.close();
				}
	
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}finally {
				HttpSession hs = req.getSession();
				hs.setAttribute("ImageFileList", ImageFileList);
			}
		}else {
			System.out.println("이미지 url주소가 비어있습니다.");
		}
	
	}
	
	@SuppressWarnings("unchecked")
	public void imgToZip(HttpServletRequest req) {
		
		ArrayList<String> fileList;
		String directory = ServletContext.getRealPath("fileUpDown")+"/";
		String zipFileName = ServletContext.getRealPath("compressedFile")+"/"+generateFilename(".zip");
		req.getSession().setAttribute("zipFileName", zipFileName);
		ArrayList<String> ZipFileList = checkListInSession("ZipFileList", req.getSession());

		ZipFileList.add(zipFileName);
		
		if(req.getParameterValues("selectedOption").length == 0 ) {//체크박스가 하나도 체크되지 않은경우 이미지 전체
			 fileList = (ArrayList<String>) req.getSession().getAttribute("ImageFileList");
		}else {
			ArrayList<String> SelectedFileList = new ArrayList<String>();
			String[] selectedOption = req.getParameterValues("selectedOption");
			for(String selected : selectedOption) {
				SelectedFileList.add(selected);
			}
			fileList = SelectedFileList;
		}
		
		try {
			FileOutputStream fos = new FileOutputStream(zipFileName);
			ZipOutputStream zos = new ZipOutputStream(fos);
			
			for(String file:fileList) {
				addToZipFile(directory+file, zos, file);
			}
			
			req.getSession().setAttribute("ZipFileList", ZipFileList);

			zos.close();
			fos.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	private void addToZipFile(String fileName, ZipOutputStream zos, String entryName) throws IOException {
	       
			File file = new File(fileName);
	        FileInputStream fis = new FileInputStream(file);
	        ZipEntry zipEntry = new ZipEntry(entryName);
	        zos.putNextEntry(zipEntry);

	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = fis.read(buffer)) > 0) {
	            zos.write(buffer, 0, length);
	        }

	        fis.close();
	        zos.closeEntry();
	}
	
	public String isValidURL(String url) { //url이 유효한지 체크
	    try {

	        URL parsedURL = new URL(url);
	        HttpURLConnection connection = (HttpURLConnection) parsedURL.openConnection();
	        connection.setRequestMethod("HEAD");

	        // HTTP 응답 코드를 확인하여 유효한 URL 여부를 판단
	        int responseCode = connection.getResponseCode();

	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            return url; // URL이 정상적으로 열림
	        } else {
	            return null; // URL이 열리지 않음
	        }
	    } catch (IOException e) {
	        return null; // 유효하지 않은 URL
	    }
	}
	
	
	public String isValidURL(String url,HttpServletRequest req) { //이미지 url이 유효한지 체크
	    try {
	    	
	    	System.out.println("imgURL 유효성 체크");
	        if (url.startsWith("//")) {//프로토콜이 생략된 url
	        	URL u = new URL((String)req.getSession().getAttribute("URL"));
	        	url = u.getProtocol() +":"+ url;
	        	System.out.println("프로토콜 추가됨 :"+url);
	        }else if(url.startsWith("/")) {//상대 주소를 나타내는 url 앞에 프로토콜과 도메인을 붙인다.
	        	url = getDomainAndProtocol((String)req.getSession().getAttribute("URL")) + url;
	        }

	        URL parsedURL = new URL(url);
	        HttpURLConnection connection = (HttpURLConnection) parsedURL.openConnection();
	        
	        connection.setRequestMethod("HEAD");

	        // HTTP 응답 코드를 확인하여 유효한 URL 여부를 판단
	        int responseCode = connection.getResponseCode();

	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            return url; // URL이 정상적으로 열림
	        } else {
//	        	if(responseCode == 500) {
//	    	            Thread.sleep(1500); // 1.5초 딜레이(요청의 응답이 불안정한 경우 서버의 부하를 방지하기 위한 딜레이입니다.)
//	        	}	이 부분은 이미지 url의 서버 문제이므로 제가 해결하기는 어려웠습니다.
//	        		그냥 서버문제가 발생한 이미지는 가져오지 않는것으로 처리하겠습니다.
	        	System.out.println(responseCode);
	        	System.out.println("valid:no - connection denied");
	            return null; // URL이 열리지 않음
	            
	        }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	System.out.println(e.getMessage());
	    	System.out.println("valid:no - Exeception occured");
	        return null; // 유효하지 않은 URL
	    }
	}
	
	public String getDomainAndProtocol(String urlString) {
		try {
			URL url = new URL(urlString);
			String domain = url.getHost();
			String protocol = url.getProtocol();
			return protocol+"://"+domain;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "";
		}

	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> checkListInSession(String listName,HttpSession hs){
		
		if(hs.getAttribute(listName)!=null) {
			return (ArrayList<String>) hs.getAttribute(listName);
		}
		
		return new ArrayList<String>();
	}
	
	
	public void stackToStorage(HttpServletRequest req) {
		if(loginYes(req)) {
			System.out.println("저장소로 옮길 파일리스트");
			SqueezeUser su = (SqueezeUser) req.getSession().getAttribute("user");
			String folder = su.getSqueeze_folder();
			String[] fileList = req.getParameterValues("selectedOption");
			String sourcePath = ServletContext.getRealPath("fileUpDown")+"/";
			String storagePath = ServletContext.getRealPath("UserFolders")+"/";
			for(String f : fileList) {
				String sourceFilePath = sourcePath + f;
				String folderPath = storagePath + folder + "/";
				
				System.out.println(sourceFilePath);
				System.out.println(folderPath);
			}
		}
	}
	
	public boolean loginYes(HttpServletRequest req) {
		if(req.getSession().getAttribute("login")!=null) {
			if(req.getSession().getAttribute("login").equals("y")) {
				return true;
			}
		}
		return false;
	}
	
	public void getStorageFiles(HttpServletRequest req) {
		if(req.getSession().getAttribute("login")!=null) {
			if(req.getSession().getAttribute("login").equals("y")) {
				SqueezeUser su = (SqueezeUser) req.getSession().getAttribute("user");
				File folder = new File(ServletContext.getRealPath("UserFolders")+"/"+su.getSqueeze_folder());
				System.out.println(folder.toString());
			}else {
				System.out.println("로그인 상태아님");
			}
		}else {
			System.out.println("로그인 상태 아님");
		}
	}

}//class end
