import javax.swing.*;  //�����׼�
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

import java.text.*; //�ṩDateFormat���׼�
import java.util.*; //�ṩLocale���׼�
import javax.swing.text.*;  //�ṩDefaultFormatter, ������e���׼�

import java.beans.*; //�ṩPropertyChangeListener������׼�

public class Ex_501_4 extends JFrame {

	//���x��ʽ���Y��ݔ��Ę�ʽ
	static final String ID_PATTERN = "?#########"; //����C̖�a�����֘�ʽ
	static final String PHONE_PATTERN = "####-###-###"; //�Ԓ�����֘�ʽ
	static final String EMAIL_PATTERN = "^([\\w]+)(([-\\.][\\w]+)?)*@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	JFormattedTextField ftfName = new JFormattedTextField(new String("�쳿��")),
					   ftfID,
					   ftfBirthday = new JFormattedTextField(),
					   ftfPhone,
					   ftfEmail;
	//����JFormattedTextField���

	JLabel lbName = new JLabel("������λ��text���� :       value���� : ");
	JLabel lbMsg = new JLabel("�]�� : ");
	JButton enable = new JButton("enable");
	JButton disable = new JButton("disable");
//	JButton iconBtn = new JButton(new ImageIcon(".\\Icon\\Bitc.gif"));
	JLabel J=new JLabel("���� : ", 
			new ImageIcon(".\\Icon\\Bitc.gif"), SwingConstants.RIGHT);
	//����ָ��o���

	//���x�K����O �����
	ActionListener al = new ActionListener(){
		public void actionPerformed(ActionEvent e){

			//ȡ�Ä��������ִ�, �K�Д��|�l�¼��İ��o
			if(e.getActionCommand().equals("enable")){
				enable.setEnabled(false); //���������O�����o�Ƿ���Ч
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
		enable.setActionCommand("enable"); //�O�����o�Ą��������ִ�
		disable.setActionCommand("disable");

		disable.setEnabled(false); //�����o����ʼ��B�O����oЧ
		J.setEnabled(false);

		enable.addActionListener(al); //�]�ԱO ��
		disable.addActionListener(al);


		//�O �Θ��Ƅ��¼�, �K�@ʾtext�����cvalue����ֵ֮��׃��
		ftfName.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e){
				JFormattedTextField ftfSource = (JFormattedTextField) e.getSource();

				lbName.setText("������λ��text���� : " 
											+ ftfSource.getText()
											+ "  value���� : " + ftfSource.getValue());
			}
		});

		//�O Action�¼�, �K�@ʾtext�����cvalue����֮׃��
		ftfName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				JFormattedTextField ftfSource = (JFormattedTextField) e.getSource();

				lbName.setText("������λ��text���� : " 
											+ ftfSource.getText()
											+ "  value���� : " + ftfSource.getValue());
			}
		});

		try{
			ftfID = new JFormattedTextField(
						new DefaultFormatterFactory(new MaskFormatter(ID_PATTERN)),
						new String("A154688887"));
			//����JFormattedTextField���,
			//�K���뽨��MaskFormatter��ʽ�����
			//DefaultFormatterFactory����c�A�Oֵ
			ftfID.setInputVerifier(new IDVerifier()); //�O����C����C̖�a�����
			MaskFormatter mfPhone = new MaskFormatter(PHONE_PATTERN);
			//����MaskFormatter���

			mfPhone.setPlaceholderCharacter('_'); //�O����λ�@ʾݔ��λ�õĘ�ʾ
			mfPhone.setValidCharacters("0123456789 ");
			//�O���ɽ��ܵ���Ԫ��0��9�Ĕ����c�հ�

			ftfPhone = new JFormattedTextField(mfPhone);
			//����JFormattedTextField���,
			//ֱ�ӂ�����ʹ�õ�MaskFormatter��ʽ���
		}
		catch(ParseException pe){
			pe.printStackTrace();
		}

		ftfPhone.setValue(new String("0905-123-456"));
		//�O��value����
		ftfPhone.setInputVerifier(new PhoneFormatVerifier(PHONE_PATTERN)); //�O����C�Ԓ̖�a����C���
		DateFormat dfDate = 
						new SimpleDateFormat("yyyy/MM/dd", Locale.US);
		//�������ڸ�ʽ

		ftfBirthday.setFormatterFactory(
						new DefaultFormatterFactory(new DateFormatter(dfDate)));
		//�O���a����ʽ����Ľ�����

		Calendar date = Calendar.getInstance(Locale.TAIWAN);
		//ȡ�ñ�ʾ����Calendar���

		date.set(1976, 5, 16);
		//�O������ֵ��1976/6/16, �·�ֵ��0�_ʼ, ����5����6��

		ftfBirthday.setValue(date.getTime()); //�O��value����
		ftfBirthday.setInputVerifier(new DateVerifier(new Date()));
		ftfEmail = new JFormattedTextField(
				String.format(EMAIL_PATTERN));
		ftfEmail.setValue("526452732@qq.com");
		ftfEmail.addPropertyChangeListener("value", new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent evt){

				//Double value = (Double) evt.getNewValue();
				//�������^setMinimum()�����csetMaximum()����,
				//����һ�Д��������������

				String value = evt.getNewValue().toString();
				if(value.matches(EMAIL_PATTERN)){
					lbMsg.setText("�]��: " + value);
				}
				//ȡ�õ��Y���̈́e��String

				else lbMsg.setText("�]���ʽ�e�`");
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
		
		jpSub.add(J); //��Ԫ������JPanel������
		jpSub.add(ftfName);
		jpStyle02.add(sprSytle02);
		jpSub1.add(new JLabel("����C��̖ : ", JLabel.RIGHT));
		jpSub1.add(ftfID);
		
		jpSub1.add(new JLabel("���� : ", JLabel.RIGHT));
		jpSub1.add(ftfBirthday);
		
		jpSub2.add(new JLabel("�j�Ԓ : ", JLabel.RIGHT));
		jpSub2.add(ftfPhone);
		
		jpSub2.add(new JLabel("�]�� : ", JLabel.RIGHT));
		jpSub2.add(ftfEmail);
		TitledBorder titledbordertop = new TitledBorder("�j");
//		jpSub1.setBorder(titledbordertop); //�O���˻`ʹ�Ø��}��
		jpSub2.setBorder(titledbordertop); //�O���˻`ʹ�Ø��}��
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
		
		Container cp = getContentPane(); //ȡ�Ã������
		cp.add(jpMain); //��������������
		cp.add(jpLabel, BorderLayout.SOUTH);
		
		int gap = 10; //�հ׿򾀵Č���
		getRootPane().setBorder(
			BorderFactory.createEmptyBorder(gap, gap, gap, gap));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�O���P�]ҕ�����A�O�Y����ʽ
		setSize(310, 350); //�O��ҕ����ܵ��@ʾ��С
		setVisible(true); //�@ʾҕ�����
		}
	//��Cݔ�������C̖�a�Ƿ����_
		class IDVerifier extends InputVerifier {

			public boolean verify(JComponent input){
			
				//�Д���������C��Ԫ���Ƿ��JFormattedTextField
				if(!(input instanceof JFormattedTextField))
					return true;

				JFormattedTextField ftf = (JFormattedTextField) input; //�D�Q�̈́e

				String ID = (String) ftf.getText(); //ȡ�Ù�λ�Č���ֵ

				if (ID.length() != 10) //�Д�ݔ���ִ����L��
					return false;
				
				ID = ID.toUpperCase(); //��Ӣ����ĸ���D�Q���
				byte s[] = ID.getBytes();
				//��������Ԫ�D�Q��byte�̈́e, byteֵ������Ԫ��ASCII�a

				if (s[0] >= 65 && s[0] <= 90) { //�Д��һ����Ԫ�Ƿ�Ӣ����ĸ

					int a[] = {10, 11, 12, 13, 14, 15, 16, 17, 34, 18, 19, 20, 21,
						          22, 35, 23, 24, 25, 26, 27, 28, 29, 32, 30, 31, 33};
					//�����cӢ����ĸ����������ֵ

					int count = (a[ s[0] - 65] / 10)  + (a[ s[0] - 65] % 10) * 9;
					//������춵�һ����Ԫ������ֵ, ʮλ�Ĕ��ּ��ς�λ�Ĕ��ֳ���9

					for (int i = 1; i <= 8; i++) {
						count += (s[i] - 48) * (9 - i); //�������������1��8�Ĕ���, Ȼ���ۼ�
					}

					//��10�pȥ�ۼӽY���Ă�λ����, Ȼ���c�z��a�Ȍ�
					if ((10 - (count % 10)) == (s[9] - 48)) {
						lbMsg.setText(" [" + ID + "] ͨ�^��C! ");
						return true;
					}
				}

				lbMsg.setText(" [" + ID + "] �o��ͨ�^��C! ");
				return false;
			}
		}

		//���x��C�Ԓ̖�a��ʽ�Ƿ����_��e
		class PhoneFormatVerifier extends InputVerifier {

			String pattern;

			PhoneFormatVerifier(String phonePattern) { //������
				pattern = phonePattern;
			}

			public boolean verify(JComponent input) {

				//�Д���������C��Ԫ���Ƿ��JFormattedTextField
				if(!(input instanceof JFormattedTextField))
					return true;

				JFormattedTextField ftf = (JFormattedTextField) input; //�D�Q�̈́e

				JFormattedTextField.AbstractFormatter
								formatter = ftf.getFormatter(); //ȡ�ø�ʽ���

				if(formatter == null){
					lbMsg.setText("�Ԓ̖�a��ʽͨ�^��C");
					return true;
				}

				try{
					formatter.stringToValue((String)ftf.getValue()); //�D�Q��λ�ȵ�ֵ
					lbMsg.setText("�Ԓ̖�a��ʽͨ�^��C");
					return true;
				}
				catch(ParseException pe){
					lbMsg.setText("��ʽ�e�`, ���_�ĸ�ʽ��" + pattern);
					return false;
				}
			}
		}

		//�z��ݔ������ֵ�Ƿ����_����Ce
		class DateVerifier extends InputVerifier {

			Date d;
		
			public DateVerifier(Date d){
				this.d = d;
			}

			public boolean verify(JComponent input) {

				//�Д���������C��Ԫ���Ƿ��JFormattedTextField
				if(!(input instanceof JFormattedTextField))
					return true;

				JFormattedTextField ftf = (JFormattedTextField) input; //�D�Q�̈́e

				if(d.after((Date)ftf.getValue())){ //�z��ݔ������ֵ�Ƿ���ָ��������
					lbMsg.setText("����ݔ��ֵͨ�^��C");
					return true;
				}

				lbMsg.setText("�������" + d + ", ݔ��ֵ�����_! ");
				return false;
			}
		}

	public static void main(String args[]) throws ParseException {
		new Ex_501_4(); //����ҕ��������
	}
}