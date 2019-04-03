package com.gitee.taven;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.gitee.taven.listener.ExcelListener;
import com.gitee.taven.model.EasyModel;
import com.gitee.taven.model.MultiLineHeadExcelModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@RestController
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@GetMapping("easyRead")
	public Object easyRead() throws FileNotFoundException {
		File excel = ResourceUtils.getFile("classpath:read1.xls");
		InputStream inputStream = new BufferedInputStream(new FileInputStream(excel));

		Object result = null;
		try {
//			ExcelListener listener = new ExcelListener();
			result = EasyExcelFactory.read(inputStream, new Sheet(1, 1, EasyModel.class));

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	@GetMapping("easyWrite")
	public void easyWrite(HttpServletResponse response) throws IOException {
		ServletOutputStream out = response.getOutputStream();
		String fileName = new String(("UserInfo " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
				.getBytes(), "UTF-8");
		response.setContentType("multipart/form-data");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-disposition", "attachment;filename="+fileName+".xlsx");

		ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
		Sheet sheet1 = new Sheet(1, 0, EasyModel.class);
		sheet1.setAutoWidth(true);
		writer.write(getEasyData(), sheet1);
		writer.finish();
		out.flush();
	}

	private List<EasyModel> getEasyData() {
		List<EasyModel> list = new ArrayList<>();
		list.add(new EasyModel("zhangsan", 18, new Date(), new BigDecimal("10")));
		list.add(new EasyModel("lisi", 18, new Date(), new BigDecimal("12.56")));
		list.add(new EasyModel("王五", 18, new Date(), new BigDecimal("13.00")));
		return list;
	}

	@GetMapping("mutilHeadRead")
	public Object mutilHeadRead() throws FileNotFoundException {
		File excel = ResourceUtils.getFile("classpath:read1.xls");
		InputStream inputStream = new BufferedInputStream(new FileInputStream(excel));

		Object result = null;
		try {
//			ExcelListener listener = new ExcelListener();
			result = EasyExcelFactory.read(inputStream, new Sheet(2, 3, MultiLineHeadExcelModel.class));

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	@GetMapping("mutilHeadWrite")
	public void mutilHeadWrite(HttpServletResponse response) throws IOException {
		ServletOutputStream out = response.getOutputStream();
		String fileName = new String(("UserInfo " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
				.getBytes(), "UTF-8");
		response.setContentType("multipart/form-data");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-disposition", "attachment;filename="+fileName+".xlsx");

		ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
		Sheet sheet1 = new Sheet(1, 0, MultiLineHeadExcelModel.class);
		sheet1.setAutoWidth(true);
		writer.write(getMutilData(), sheet1);
		writer.finish();
		out.flush();
	}

	public List<MultiLineHeadExcelModel> getMutilData() {
		List<MultiLineHeadExcelModel> list = new ArrayList<>();
		for (int i=0; i<10; i++) {
			MultiLineHeadExcelModel model = new MultiLineHeadExcelModel();
			model.setP1(Integer.toString(i));
			model.setP2(Integer.toString(i));
			model.setP3(0);
			model.setP4(0L);
			model.setP5(Integer.toString(i));
			model.setP6(Integer.toString(i));
			model.setP7(Integer.toString(i));
			model.setP8(Integer.toString(i));
			model.setP9(Integer.toString(i));
			list.add(model);

		}

		return list;
	}

	@GetMapping("writeWithTemplate")
	public void writeWithTemplate(HttpServletResponse response) throws IOException {
		ServletOutputStream out = response.getOutputStream();
		String fileName = new String(("UserInfo " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
				.getBytes(), "UTF-8");
		response.setContentType("multipart/form-data");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-disposition", "attachment;filename="+fileName+".xlsx");

		InputStream in = FileUtil.getResourcesFileInputStream("template.xlsx");
		ExcelWriter writer = EasyExcelFactory.getWriterWithTemp(in, out, ExcelTypeEnum.XLSX,false);
		Sheet sheet1 = new Sheet(1, 1, EasyModel.class, "sheet1", null);
		sheet1.setAutoWidth(true);
		sheet1.setStartRow(0);
		writer.write(getEasyData(), sheet1);
		writer.finish();
		out.flush();
		in.close();
	}

	public static class FileUtil {

		public static InputStream getResourcesFileInputStream(String fileName) {
			return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);
		}

	}
}

