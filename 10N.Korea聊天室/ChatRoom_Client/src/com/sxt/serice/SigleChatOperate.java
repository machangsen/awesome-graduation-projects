package com.sxt.serice;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.plaf.LabelUI;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.sxt.request.Communication;
import com.sxt.request.StartApp;
import com.sxt.swing.OneChatPanel;
import com.sxt.swing.SigleChatSwing;
import com.sxt.util.FontAndText;
import com.sxt.util.PicInfo;
import com.sxt.vo.TransMsg;
import com.sxt.vo.Users;
import com.sxt.windows.ChatPic;
import com.sxt.windows.CoolToolTip;

/**
 * 用于私聊页面siglechatswing界面的相关操作
 * @author penglijun
 *
 */
public class SigleChatOperate implements MouseListener,ActionListener{
	private static SigleChatSwing sigleChatSwing;	
	private JComboBox fontStyle;
	private JComboBox fontSize;
	private JComboBox fontColor;
	private boolean flag = true;
	private Users users=null; //当前聊天对象
	
	/**
	 * 获取当前siglechatswing界面
	 * @return
	 */
	public SigleChatSwing getSigleChatSwing() {
		return sigleChatSwing;
	}


	/**
	 * 设置当前singlechatswing界面
	 * @param sigleChatSwing
	 */
	public void setSigleChatSwing(SigleChatSwing sigleChatSwing) {
		this.sigleChatSwing = sigleChatSwing;
	}


	/**
	 * 构造方法
	 * 初始化界面和添加相关监听事件
	 * @param users
	 */
	public SigleChatOperate(Users users) {
		sigleChatSwing=new SigleChatSwing();
		sigleChatSwing.addMouseListener(this);
		sigleChatSwing.getSendText().addMouseListener(this);
		sigleChatSwing.getSendButton().addActionListener(this);
		sigleChatSwing.getTextPane().addMouseListener(this);
		sigleChatSwing.getEmojiButton().addMouseListener(this);
		sigleChatSwing.getFontButton().addActionListener(this);
		sigleChatSwing.getFriendMsgLabel().setText(users.getName());
		this.users=users;
//		Thread thread=new Thread(this);
//		this.run();
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}


	/**
	 * 鼠标press事件
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		sigleChatSwing.getPicsJWindow().setVisible(false);
	}


	/**
	 * 鼠标释放事件
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (arg0.getButton()!=1) {//不是左键
			return; 
		}
		JComponent source =null;
		try {
			source= (JComponent) arg0.getSource();
		} catch (ClassCastException e) {
			return;
		}
		if (arg0.getX() >= 0 && arg0.getX() <= source.getWidth() && arg0.getY() >= 0
				&& arg0.getY() <= source.getHeight()){
			if (source.equals(sigleChatSwing.getEmojiButton())) {
				sigleChatSwing.getPicsJWindow().setVisible(true);
				sigleChatSwing.getPicsJWindow().setLocation(400,500);
			}
		}
	}
	
	/**
	 * 用于图片信息的内部类
	 * @author pengl
	 *
	 */
	class PicInfo{
		/* 图片信息*/
		int pos;
		String val;
		public PicInfo(int pos,String val){
			this.pos = pos;
			this.val = val;
		}
		public int getPos() {
			return pos;
		}
		public void setPos(int pos) {
			this.pos = pos;
		}
		public String getVal() {
			return val;
		}
		public void setVal(String val) {
			this.val = val;
		}
		
	}

	/**
	 * 在私聊天页面上画出font设置框
	 * @param fontColor 
	 */
	public void setFontWindow() {
		sigleChatSwing.getMessagePane().setBounds(0, 70, 978, 368);
		fontStyle = new JComboBox();
		fontStyle.setBackground(new Color(40, 96, 144));
		fontStyle.setFont(new Font("微软雅黑 Light", Font.PLAIN, 20));
		fontStyle.setModel(new DefaultComboBoxModel(new String[] { "字体样式", "微软雅黑 Light", "宋体", "楷体", "仿宋", "华文隶书", "黑体" }));

		fontStyle.setBounds(20, 440, 131, 43);
		sigleChatSwing.getContentPane().add(fontStyle);
		fontSize = new JComboBox();
		fontSize.setBackground(new Color(40, 96, 144));
		fontSize.setFont(new Font("微软雅黑 Light", Font.PLAIN, 20));

		fontSize.setModel(new DefaultComboBoxModel(new String[] { "字体大小", "14", "16", "18", "20", "22", "24", "26", "28" }));
		fontSize.setBounds(180, 440, 131, 43);
		sigleChatSwing.getContentPane().add(fontSize);
		
		fontColor= new JComboBox();
		fontColor.setBackground(new Color(40, 96, 144));
		fontColor.setFont(new Font("微软雅黑 Light", Font.PLAIN, 20));
		fontColor.setModel(new DefaultComboBoxModel(new String[] {"字体颜色","黑色", "红色", "黄色","绿色","蓝色"}));
		fontColor.setBounds(340, 440, 131, 43);
		sigleChatSwing.getContentPane().add(fontColor);
	}


	/**
	 * 字体和发送接听事件
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		/**
		 * 字体设置按钮监听事件
		 */
		if (arg0.getSource().equals(sigleChatSwing.getFontButton())) {
			if (flag) {
				setFontWindow();
				fontStyle.addActionListener(this);
				fontSize.addActionListener(this);
				flag = false;
			} else {
				sigleChatSwing.getMessagePane().setBounds(0, 70, 978, 418);
				flag = true;
			}
		}
		/**
		 * 发送按钮监听事件
		 */
		if (arg0.getSource().equals(sigleChatSwing.getSendButton())) {
			Communication c=new Communication();
			ArrayList<Users> usersList=new ArrayList<>();
			usersList.add(StartApp.getList().get(0));
			usersList.add(users);
			c.sender(new TransMsg("SigleChat",usersList,sigleChatSwing.sendMsg()));
			sigleChatSwing.getSendText().setText(null);
		}
		
	}
	

}
