import javax.swing.*;  //引用套件
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

import java.text.*; //提供DateFormat的套件
import java.util.*; //提供Locale的套件
import javax.swing.text.*;  //提供DefaultFormatter, 及其子類別的套件

import java.beans.*; //提供PropertyChangeListener介面的套件

public class Ex_501_4 extends JFrame {

	//定義格式化資料輸入的樣式
	static final String ID_PATTERN = "?#########"; //身份證號碼的遮罩樣式
	static final String PHONE_PATTERN = "####-###-###"; //電話的遮罩樣式
	static final String EMAIL_PATTERN = "^([\\w]+)(([-\\.][\\w]+)?)*@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	JFormattedTextField ftfName = new JFormattedTextField(new String("徐晨曄")),
					   ftfID,
					   ftfBirthday = new JFormattedTextField(),
					   ftfPhone,
					   ftfEmail;
	//宣告JFormattedTextField物件

	JLabel lbName = new JLabel("姓名欄位的text屬性 :       value屬性 : ");
	JLabel lbMsg = new JLabel("郵箱 : ");
	JButton enable = new JButton("enable");
	JButton disable = new JButton("disable");
//	JButton iconBtn = new JButton(new ImageIcon(".\\Icon\\Bitc.gif"));
	JLabel J=new JLabel("姓名 : ", 
			new ImageIcon(".\\Icon\\Bitc.gif"), SwingConstants.RIGHT);
	//宣告指令按鈕物件

	//定義並宣告監聽器物件
	ActionListener al = new ActionListener(){
		public void actionPerformed(ActionEvent e){

			//取得動作命令字串, 並判斷觸發事件的按鈕
			if(e.getActionCommand().equals("enable")){
				enable.setEnabled(false); //依照需求設定按鈕是否有效
				disable.setEnabled(true);
				J.setEnabled(true);
			}
			else{
				enable.setEnabled(true);
				disable.setEnabled(false);
				J.setEnabled(false);
			}
		}	
	};
	Ex_501_4() throws ParseException{
		enable.setActionCommand("enable"); //設定按鈕的動作命令字串
		disable.setActionCommand("disable");

		disable.setEnabled(false); //將按鈕的起始狀態設定為無效
		J.setEnabled(false);

		enable.addActionListener(al); //註冊監聽器
		disable.addActionListener(al);


		//監聽游標移動事件, 並顯示text屬性與value屬性之值的變化
		ftfName.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e){
				JFormattedTextField ftfSource = (JFormattedTextField) e.getSource();

				lbName.setText("姓名欄位的text屬性 : " 
											+ ftfSource.getText()
											+ "  value屬性 : " + ftfSource.getValue());
			}
		});

		//監聽Action事件, 並顯示text屬性與value屬性之變化
		ftfName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				JFormattedTextField ftfSource = (JFormattedTextField) e.getSource();

				lbName.setText("姓名欄位的text屬性 : " 
											+ ftfSource.getText()
											+ "  value屬性 : " + ftfSource.getValue());
			}
		});

		try{
			ftfID = new JFormattedTextField(
						new DefaultFormatterFactory(new MaskFormatter(ID_PATTERN)),
						new String("A154688887"));
			//宣告JFormattedTextField物件,
			//並傳入建立MaskFormatter格式物件的
			//DefaultFormatterFactory物件與預設值
			ftfID.setInputVerifier(new IDVerifier()); //設定驗證身份證號碼的物件
			MaskFormatter mfPhone = new MaskFormatter(PHONE_PATTERN);
			//宣告MaskFormatter物件

			mfPhone.setPlaceholderCharacter('_'); //設定欄位顯示輸入位置的標示
			mfPhone.setValidCharacters("0123456789 ");
			//設定可接受的字元為0至9的數字與空白

			ftfPhone = new JFormattedTextField(mfPhone);
			//宣告JFormattedTextField物件,
			//直接傳入欲使用的MaskFormatter格式物件
		}
		catch(ParseException pe){
			pe.printStackTrace();
		}

		ftfPhone.setValue(new String("0905-123-456"));
		//設定value屬性
		ftfPhone.setInputVerifier(new PhoneFormatVerifier(PHONE_PATTERN)); //設定驗證電話號碼的驗證物件
		DateFormat dfDate = 
						new SimpleDateFormat("yyyy/MM/dd", Locale.US);
		//宣告日期格式

		ftfBirthday.setFormatterFactory(
						new DefaultFormatterFactory(new DateFormatter(dfDate)));
		//設定產生格式物件的建立器

		Calendar date = Calendar.getInstance(Locale.TAIWAN);
		//取得表示日期Calendar物件

		date.set(1976, 5, 16);
		//設定日期值為1976/6/16, 月份值從0開始, 故以5代表6月

		ftfBirthday.setValue(date.getTime()); //設定value屬性
		ftfBirthday.setInputVerifier(new DateVerifier(new Date()));
		ftfEmail = new JFormattedTextField(
				String.format(EMAIL_PATTERN));
		ftfEmail.setValue("526452732@qq.com");
		ftfEmail.addPropertyChangeListener("value", new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent evt){

				//Double value = (Double) evt.getNewValue();
				//因為呼叫過setMinimum()方法與setMaximum()方法,
				//故上一行敘述亦可正常執行

				String value = evt.getNewValue().toString();
				if(value.matches(EMAIL_PATTERN)){
					lbMsg.setText("郵箱: " + value);
				}
				//取得的資料型別為String

				else lbMsg.setText("郵箱格式錯誤");
			}
		});
		
		JPanel jpMain = new JPanel(null);
		JPanel jpSub = new JPanel(new GridLayout(1, 2,  0, 0));
		JSeparator sprSytle02 = new JSeparator();
		Dimension dimStyle02 = sprSytle02.getPreferredSize();
		sprSytle02.setPreferredSize(
							new Dimension(100, dimStyle02.height));
	
		JPanel jpStyle02 = new JPanel(new GridLayout(1,1));
		JPanel jpSub1 = new JPanel(new GridLayout(2, 2,  0, 0));
		JPanel jpSub2 = new JPanel(new GridLayout(2, 2,  0, 0));
		
		jpSub.setBounds(10, 0, 260, 30);
		jpStyle02.setBounds(10, 32, 260, 1);
		jpSub1.setBounds(10, 36, 260, 60);
		jpSub2.setBounds(10, 96, 260, 90);
		
		jpSub.add(J); //將元件加入JPanel子容器
		jpSub.add(ftfName);
		jpStyle02.add(sprSytle02);
		jpSub1.add(new JLabel("身份證字號 : ", JLabel.RIGHT));
		jpSub1.add(ftfID);
		
		jpSub1.add(new JLabel("生日 : ", JLabel.RIGHT));
		jpSub1.add(ftfBirthday);
		
		jpSub2.add(new JLabel("聯絡電話 : ", JLabel.RIGHT));
		jpSub2.add(ftfPhone);
		
		jpSub2.add(new JLabel("郵箱 : ", JLabel.RIGHT));
		jpSub2.add(ftfEmail);
		TitledBorder titledbordertop = new TitledBorder("聯絡");
//		jpSub1.setBorder(titledbordertop); //設定標籤使用標題框線
		jpSub2.setBorder(titledbordertop); //設定標籤使用標題框線
		JPanel jpButton = new JPanel(new GridLayout(1, 2,  5, 5));
		jpButton.add(enable);
		jpButton.add(disable);
		JPanel jpLabel = new JPanel(new GridLayout(3, 1,  5, 5));
		jpLabel.add(jpButton);
		jpLabel.add(lbName);
		jpLabel.add(lbMsg);
		jpMain.add(jpSub);
		jpMain.add(jpStyle02);
		jpMain.add(jpSub1);
		jpMain.add(jpSub2);
		
		Container cp = getContentPane(); //取得內容面版
		cp.add(jpMain); //將面版加入內容面版
		cp.add(jpLabel, BorderLayout.SOUTH);
		
		int gap = 10; //空白框線的寬度
		getRootPane().setBorder(
			BorderFactory.createEmptyBorder(gap, gap, gap, gap));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//設定關閉視窗將預設結束程式
		setSize(310, 350); //設定視窗框架的顯示大小
		setVisible(true); //顯示視窗框架
		}
	//驗證輸入的身份證號碼是否正確
		class IDVerifier extends InputVerifier {

			public boolean verify(JComponent input){
			
				//判斷欲執行驗證的元件是否為JFormattedTextField
				if(!(input instanceof JFormattedTextField))
					return true;

				JFormattedTextField ftf = (JFormattedTextField) input; //轉換型別

				String ID = (String) ftf.getText(); //取得欄位的屬性值

				if (ID.length() != 10) //判斷輸入字串的長度
					return false;
				
				ID = ID.toUpperCase(); //將英文字母均轉換為大寫
				byte s[] = ID.getBytes();
				//將所有字元轉換為byte型別, byte值即為字元的ASCII碼

				if (s[0] >= 65 && s[0] <= 90) { //判斷第一個字元是否英文字母

					int a[] = {10, 11, 12, 13, 14, 15, 16, 17, 34, 18, 19, 20, 21,
						          22, 35, 23, 24, 25, 26, 27, 28, 29, 32, 30, 31, 33};
					//儲存與英文字母對應的整數值

					int count = (a[ s[0] - 65] / 10)  + (a[ s[0] - 65] % 10) * 9;
					//將對應於第一個字元的整數值, 十位的數字加上個位的數字乘以9

					for (int i = 1; i <= 8; i++) {
						count += (s[i] - 48) * (9 - i); //將數字依序乘上1至8的數字, 然後累加
					}

					//以10減去累加結果的個位數字, 然後與檢查碼比對
					if ((10 - (count % 10)) == (s[9] - 48)) {
						lbMsg.setText(" [" + ID + "] 通過驗證! ");
						return true;
					}
				}

				lbMsg.setText(" [" + ID + "] 無法通過驗證! ");
				return false;
			}
		}

		//定義驗證電話號碼格式是否正確的類別
		class PhoneFormatVerifier extends InputVerifier {

			String pattern;

			PhoneFormatVerifier(String phonePattern) { //建構子
				pattern = phonePattern;
			}

			public boolean verify(JComponent input) {

				//判斷欲執行驗證的元件是否為JFormattedTextField
				if(!(input instanceof JFormattedTextField))
					return true;

				JFormattedTextField ftf = (JFormattedTextField) input; //轉換型別

				JFormattedTextField.AbstractFormatter
								formatter = ftf.getFormatter(); //取得格式物件

				if(formatter == null){
					lbMsg.setText("電話號碼格式通過驗證");
					return true;
				}

				try{
					formatter.stringToValue((String)ftf.getValue()); //轉換欄位內的值
					lbMsg.setText("電話號碼格式通過驗證");
					return true;
				}
				catch(ParseException pe){
					lbMsg.setText("格式錯誤, 正確的格式為" + pattern);
					return false;
				}
			}
		}

		//檢查輸入日期值是否正確的驗證類別
		class DateVerifier extends InputVerifier {

			Date d;
		
			public DateVerifier(Date d){
				this.d = d;
			}

			public boolean verify(JComponent input) {

				//判斷欲執行驗證的元件是否為JFormattedTextField
				if(!(input instanceof JFormattedTextField))
					return true;

				JFormattedTextField ftf = (JFormattedTextField) input; //轉換型別

				if(d.after((Date)ftf.getValue())){ //檢查輸入日期值是否在指定日期後
					lbMsg.setText("日期輸入值通過驗證");
					return true;
				}

				lbMsg.setText("日期晚於" + d + ", 輸入值不正確! ");
				return false;
			}
		}

	public static void main(String args[]) throws ParseException {
		new Ex_501_4(); //宣告視窗框架物件
	}
}