package kg.kg;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class Excel{
	
	private static MongoClient mongoClient;
	private static MongoDatabase mongoDatabase;
	private static MongoCollection<Document> mongoCollection;
	static {
        try {
			mongoClient = new MongoClient("192.168.1.207", 27017);
			mongoDatabase = mongoClient.getDatabase("xz_sys");
			mongoCollection = mongoDatabase.getCollection("websites");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, List<String []> values, HSSFWorkbook wb) {

		// ��һ��������һ��HSSFWorkbook����Ӧһ��Excel�ļ�
		if (wb == null) {
			wb = new HSSFWorkbook();
		}

		// �ڶ�������workbook�����һ��sheet,��ӦExcel�ļ��е�sheet
		HSSFSheet sheet = wb.createSheet(sheetName);

		// ����������sheet����ӱ�ͷ��0��,ע���ϰ汾poi��Excel����������������
		HSSFRow row = sheet.createRow(0);

		// ���Ĳ���������Ԫ�񣬲�����ֵ��ͷ ���ñ�ͷ����
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // ����һ�����и�ʽ

		// �����ж���
		HSSFCell cell = null;

		// ��������
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(style);
		}

		// ��������
		for (int i = 0; i < values.size(); i++) {
			row = sheet.createRow(i + 1);
			for (int j = 0; j < values.get(i).length; j++) {
				// �����ݰ�˳�򸳸���Ӧ���ж���
				row.createCell(j).setCellValue(values.get(i)[j]);
			}
		}
		return wb;
	}

	public static void main(String[] args) throws Exception {
		// excel����
		String[] title = { "����", "�Ա�" };

		// excel�ļ���
		String fileName = "ѧ����Ϣ��" + System.currentTimeMillis() + ".xls";

		// sheet��
		String sheetName = "ѧ����Ϣ��";
		List<String []> content = new ArrayList<>();
		
		MongoCursor<Document> mongoCursor = mongoCollection.find().iterator();
		List<JSONObject> list = new ArrayList<>();
		while (mongoCursor.hasNext()) {
			Document document = mongoCursor.next();
			JSONObject object = new JSONObject();
			object.put("name", document.getString("name"));
			object.put("url", document.getString("url"));
			list.add(object);
		}
		for (int i = 0; i < list.size(); i++) {
			String[] a = new String[2];
			
			JSONObject obj = list.get(i);
			a[0] = obj.get("name").toString();
			a[1] = obj.get("url").toString();
			content.add(i, a);
		}

		// ����HSSFWorkbook
		HSSFWorkbook wb = Excel.getHSSFWorkbook("1", title, content, null);
		File file = new File("d:/a.xlsx");
		FileOutputStream fos = new FileOutputStream(file);
		wb.write(fos);// д�ļ�
	}
}
