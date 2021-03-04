package com.cdutcm.tcms.biz.controller;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;

import com.cdutcm.core.PathConstant;
import com.cdutcm.core.message.SysMsg;
import com.cdutcm.core.util.DateUtil;
import com.cdutcm.core.util.ResultVO;
import com.cdutcm.core.util.ResultVOUtil;
import com.cdutcm.tcms.biz.model.Emr;
import com.cdutcm.tcms.biz.model.EmrImgifo;
import com.cdutcm.tcms.biz.service.EmrService;
import com.cdutcm.tcms.biz.service.UploadService;
import com.cdutcm.tcms.sys.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
public class FileController {

	@Autowired
	private EmrService emrService;


	@Autowired
	private UploadService uploadService;
	   @RequestMapping("/photos/upload")
	   public ResultVO Upload(MultipartFile file, String visitNo, String filecontent, String patientName) throws Exception{
		   System.out.println("上传图片逻辑------------------");
	   	   String imgUrl = uploadService.saveFile(file);
		   System.out.println("图片地址="+imgUrl);
		   EmrImgifo emrImgifo = new EmrImgifo(visitNo, imgUrl, filecontent);
		   int i = emrService.savaEmrImgInfo(emrImgifo);
		   if(i!=0){
		   	return ResultVOUtil.success(imgUrl);
		   }else {
		   	return ResultVOUtil.error(9,"上传失败！");
		   }
	   }
	@GetMapping(value = "/upload/file/{file}", produces = "text/html; charset=UTF-8")
	public void excelDownload(@PathVariable("file") String encodeFile,
							  HttpServletResponse response){
		uploadService.getSavePath(encodeFile,response);
	}

	   @RequestMapping("/excel/down")
	   public String downData( String account, HttpServletResponse response) throws UnsupportedEncodingException {
		   try {
			   emrService.downData(account,response);
		   } catch (IOException e) {
			   e.printStackTrace();
		   }
//		   final File file = new File(PathConstant.IMG_PATH + account + ".xls");
//		   if (file.exists()){
//
//			   // 配置文件下载
//			   response.setHeader("content-type", "application/octet-stream");
//			   response.setContentType("application/octet-stream");
//			   // 下载文件能正常显示中文
//			   response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(account + ".xls", "UTF-8"));
//
//			   // 实现文件下载
//			   byte[] buffer = new byte[1024];
//			   FileInputStream fis = null;
//			   BufferedInputStream bis = null;
//			   try {
//				   fis = new FileInputStream(file);
//				   bis = new BufferedInputStream(fis);
//				   OutputStream os = response.getOutputStream();
//				   int i = bis.read(buffer);
//				   while (i != -1) {
//					   os.write(buffer, 0, i);
//					   i = bis.read(buffer);
//				   }
//				   System.out.println("Download the song successfully!");
//			   }
//			   catch (Exception e) {
//				   System.out.println("Download the song failed!");
//			   }
//			   finally {
//				   if (bis != null) {
//					   try {
//						   bis.close();
//					   } catch (IOException e) {
//						   e.printStackTrace();
//					   }
//				   }
//				   if (fis != null) {
//					   try {
//						   fis.close();
//					   } catch (IOException e) {
//						   e.printStackTrace();
//					   }
//				   }
//			   }
//		   }
		   return account + ".xls";
	   }

	/**
	 * 上传云平台
	 */
	public  void PostFileYPT(String path,String visitNo,String filename,String localfilename)throws ClientProtocolException, IOException {
		MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE,"--------------------HV2ymHFg03ehbqgZCaKO6jyH", Charset.defaultCharset());
		System.out.println(visitNo);
		multipartEntity.addPart("Jzlsh",new StringBody(visitNo, Charset.forName("UTF-8")));
		multipartEntity.addPart("file",new FileBody(new File(path)));
		multipartEntity.addPart("FileName",new StringBody(filename, Charset.forName("UTF-8")));
		HttpPost request = new HttpPost("http://file.cddmi.cn/MedicalAttachment/FormSave");
		request.setEntity(multipartEntity);
		request.addHeader("Content-Type","multipart/form-data; boundary=--------------------HV2ymHFg03ehbqgZCaKO6jyH");
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpResponse response =httpClient.execute(request);
		InputStream is = response.getEntity().getContent();
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		log.info("【上传返回消息:{}挂号文件名称：{}】",buffer.toString(),localfilename);

	}


//	//临时用一次
//	@RequestMapping("/yptxxxx")
//	public void yptxxxx() throws IOException {
//		getFile("D:\\QQ文件\\images");
//
//	}
//
//	private void getFile(String path) throws IOException {
//		// get file list where the path has
//		File file = new File(path);
//		// get the folder list
//		File[] array = file.listFiles();
//
//		for (int i = 0; i < array.length; i++) {
//			if (array[i].isFile()) {
//				// only take file name
//				String username=array[i].getName();
//				String filepath=array[i].getPath();
//				String visitno=array[i].getName().split("_")[0];
//				System.out.println(visitno);
//				System.out.println(username);
//				System.out.println(filepath);
//				PostFileYPTx(filepath,visitno,username);
//
//			} else if (array[i].isDirectory()) {
//				getFile(array[i].getPath());
//			}
//		}
//	}
//
//	public void PostFileYPTx(String path, String visitNo, String filename)throws ClientProtocolException, IOException {
//		MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE,"--------------------HV2ymHFg03ehbqgZCaKO6jyH", Charset.defaultCharset());
//		Emr emr=emrService.findByVisitNo(visitNo);
//		if(emr ==null){
//			System.out.println("找不到病历"+visitNo);
//		}else{
//		Date date= emr.getCreateTime();
//		String datestr= DateUtil.format(date,"yyyyMMdd");
//		String content="电子病历";
//		String uname=emr.getPatientName();
//		String suffixname=filename.split("\\.")[1];
//		System.out.println(suffixname);
//		multipartEntity.addPart("Jzlsh",new StringBody(visitNo, Charset.forName("UTF-8")));
//		multipartEntity.addPart("file",new FileBody(new File(path)));
//		multipartEntity.addPart("FileName",new StringBody(datestr+content+uname+"."+suffixname, Charset.forName("UTF-8")));
//		HttpPost request = new HttpPost("http://file.cddmi.cn/MedicalAttachment/FormSave");
//		request.setEntity(multipartEntity);
//		request.addHeader("Content-Type","multipart/form-data; boundary=--------------------HV2ymHFg03ehbqgZCaKO6jyH");
//		DefaultHttpClient httpClient = new DefaultHttpClient();
//		HttpResponse response =httpClient.execute(request);
//		InputStream is = response.getEntity().getContent();
//		BufferedReader in = new BufferedReader(new InputStreamReader(is));
//		StringBuffer buffer = new StringBuffer();
//		String line = "";
//		while ((line = in.readLine()) != null) {
//			buffer.append(line);
//		}
//		System.out.println("发送消息收到的返回："+buffer.toString());
//	}
//	}
}
