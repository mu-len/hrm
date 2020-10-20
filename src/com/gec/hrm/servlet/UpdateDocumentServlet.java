package com.gec.hrm.servlet;

import com.jdbc.hrm.Bean.Document;
import com.jdbc.hrm.Bean.Page;
import com.jdbc.hrm.Bean.User;
import com.jdbc.hrm.Dao.Impl.DocumentDaoImpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@WebServlet({"/updateDocument","/updateDocuments","/documentdownload.action","/documentadd.action","/documentaddsave.action"})
public class UpdateDocumentServlet extends HttpServlet {
    DocumentDaoImpl dmdi=new DocumentDaoImpl();//document功能实现类
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();//获取地址的后缀连接字符串
        String action = uri.substring(uri.lastIndexOf("/") + 1);
        if(action.equals("updateDocument")){//跳转到修改数据行页面
            int id = Integer.parseInt(request.getParameter("id"));
            Document document = dmdi.findOne(id);//找到需要修改的document对象
            request.setAttribute("document",document);
            request.getRequestDispatcher("WEB-INF/jsp/document/showUpdateDocument.jsp").forward(request,response);
        }else if(action.equals("updateDocuments")){
            String id="";
            String title="";
            String remark="";
            String filename="";
            String filebytes="";
            String filetype="";
            if(ServletFileUpload.isMultipartContent(request)){//判断是否为multipart/form-data post请求
                FileItemFactory factory=new DiskFileItemFactory();
                ServletFileUpload fileUpload=new ServletFileUpload(factory);
                try {
                    List<FileItem> fileItems = fileUpload.parseRequest(request);//获取请求数据
                    for(FileItem fileItem:fileItems){//遍历请求数据
                        if(!fileItem.isFormField()){//判断是否是上传数据流
                            filename=fileItem.getName();//获取上传的文件名
                            filebytes="D:\\Fos\\"+fileItem.getName();//获取服务器保存的地址
                            filetype=fileItem.getName().substring(fileItem.getName().lastIndexOf(".")+1);//获取文件的后缀名
                            if(!filetype.equals("jpg")&&!filetype.equals("png")&&!filetype.equals("jpeg")&&!filetype.equals("gif")&&!filetype.equals("bmp")){//判断并限制上传的文件类型
                                request.setAttribute("massage","修改失败,文件格式不支持");
                                request.getRequestDispatcher("WEB-INF/jsp/document/documentadd.jsp").forward(request,response);
                                return;
                            }
                            fileItem.write(new File(filebytes));
                        }else {//获取表单提交的普通项的值
                            if("id".equals(fileItem.getFieldName())){
                                id = fileItem.getString();
                                System.out.println(id);
                            }
                            if("title".equals(fileItem.getFieldName())){
                                title = fileItem.getString("UTF-8");
                                System.out.println(title);
                            }
                            if("remark".equals(fileItem.getFieldName())){
                                remark = fileItem.getString("UTF-8");
                                System.out.println(remark);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            boolean b = dmdi.updateDocument(Integer.parseInt(id), title, filename, filetype, filebytes, remark);
            if(b){//返回是否修改成功的信息
                request.setAttribute("massage","修改成功");
            }else {
                request.setAttribute("massage","修改失败");
            }
            //上传结束进行跳转
            Page<Document> page=dmdi.findPage(1,(String) request.getSession().getAttribute("title"));
            request.setAttribute("documentlist",page.getList());
            request.setAttribute("pageModel",page);
            request.getRequestDispatcher("WEB-INF/jsp/document/documentlist.jsp").forward(request,response);
        }else if(action.equals("documentdownload.action")){//documentdownload.action用于处理文件下载请求
            int id = Integer.parseInt(request.getParameter("id"));
            Document document = dmdi.findOne(id);//获取请求的下载文件对象
            ServletContext context=getServletContext();
            response.setContentType(context.getMimeType(document.getFilebytes()));
            response.setHeader("Content-Disposition","attachment;filename="+java.net.URLEncoder.encode(document.getFilename(),"UTF-8"));//设置请求头，用于下载
            InputStream inputStream = new FileInputStream(document.getFilebytes());//创建输入流
            OutputStream outputStream = response.getOutputStream();//创建输出流
            IOUtils.copy(inputStream,outputStream);//调用commons-io jar包的类，方法
        }else if(action.equals("documentadd.action")){//跳转到添加数据行页面
            request.getRequestDispatcher("WEB-INF/jsp/document/documentadd.jsp").forward(request,response);
        }else if(action.equals("documentaddsave.action")){//documentaddsave.action用于处理添加数据行请求
            String title="";
            String remark="";
            String filename="";
            String filebytes="";
            String filetype="";
            User user=(User)request.getSession().getAttribute("user_session");
            if(ServletFileUpload.isMultipartContent(request)) {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload fileUpload = new ServletFileUpload(factory);
                try {
                    List<FileItem> fileItems = fileUpload.parseRequest(request);
                    for (FileItem fileItem : fileItems) {
                        if (!fileItem.isFormField()) {
                            filename = fileItem.getName();
                            filebytes = "D:\\Fos\\" + fileItem.getName();
                            filetype = fileItem.getName().substring(fileItem.getName().lastIndexOf(".") + 1);
                            if(!filetype.equals("jpg")&&!filetype.equals("png")&&!filetype.equals("jpeg")&&!filetype.equals("gif")&&!filetype.equals("bmp")){
                                request.setAttribute("massage","添加失败,文件格式不支持");
                                request.getRequestDispatcher("WEB-INF/jsp/document/documentadd.jsp").forward(request,response);
                                return;
                            }
                            fileItem.write(new File(filebytes));
                        } else {
                            if ("title".equals(fileItem.getFieldName())) {
                                title = fileItem.getString("UTF-8");
                            }
                            if ("remark".equals(fileItem.getFieldName())) {
                                remark = fileItem.getString("UTF-8");
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            boolean b = dmdi.addDocument(title, filename, filetype, filebytes, remark, user.getId());
            if(b){
                request.setAttribute("massage","添加成功");
            }else {
                request.setAttribute("massage","添加失败");
            }
            request.getRequestDispatcher("WEB-INF/jsp/document/documentadd.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);//所有请求都在doPost进行处理
    }
}
