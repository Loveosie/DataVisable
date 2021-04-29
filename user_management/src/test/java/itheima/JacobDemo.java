package itheima;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

/**
 * @program: user_management
 * @description: 使用jacob将word转为pdf: 1.2007版本以上, jdk6以上
 *                  office 应用都可以使用jacob 操作
 * @author: lance
 * @create: 2021-04-18 10:54
 */
public class JacobDemo {
	public static void main(String[] args) {
		String source = "C:\\Users\\lance\\Downloads\\员工(李四)合同详细信息.docx";
		String target = "C:\\Users\\lance\\Downloads\\员工(李四)合同详细信息.pdf";
		ActiveXComponent app = null;
		try {
			app = new ActiveXComponent("word.application");
			app.setProperty("Visible",false);
			Dispatch docs = app.getProperty("Documents").toDispatch();
			//打开源文件
			Dispatch doc = Dispatch.call(docs, "Open", source).toDispatch();
			Dispatch.call(doc,"SaveAs",target,17);
			Dispatch.call(doc,"Close");
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			app.invoke("Quit");
		}


	}
}
