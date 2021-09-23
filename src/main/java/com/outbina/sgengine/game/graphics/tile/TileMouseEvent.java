package com.outbina.sgengine.game.graphics.tile;

import com.outbina.sgengine.game.graphics.interfaces.ITilesGridRegion;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class TileMouseEvent {
	
	private MouseEvent innerEvent;
	private TileMouseEventType eventType;
	private int row,col;
	private ITilesGridRegion region;
	
	public TileMouseEvent(
			int row ,
			int col ,
			ITilesGridRegion region,
			MouseEvent event,
			TileMouseEventType eventType) {
		this.row = row;
		this.col = col;
		this.region = region;
		this.innerEvent = event;
		this.eventType = eventType;
	}
 
	/**
	 * 获取瓷砖的行信息
	 * @return 瓷砖行
	 */
	public final int getTileRow() {
		return row;
	}
	
	/**
	 * 获取瓷砖的列信息
	 * @return 瓷砖列
	 */
	public final int getTileCol() {
		return col;
	}
	
	/**
	 * 获取瓷砖表格范围
	 * @return 瓷砖表格范围
	 */
	public ITilesGridRegion getRegion() {
		return region;
	}
	
	/**
	 * 获取在瓷砖上的鼠标操作类型
	 * @return 在瓷砖上的鼠标操作类型
	 */
	public TileMouseEventType getEventType() {
		return eventType;
	}
	
	/**
	 * 获取触发事件的源对象
	 * @return 获取触发事件的源对象
	 */
	public final Object getSource() {
		return innerEvent.getSource();
	}
	
	/**
	 * 获取在画布上的位置坐标X
	 * @return 画布上的位置坐标X
	 */
	public final double getX() {
		return innerEvent.getX();
	}

	/**
	 * 获取在画布上的位置坐标Y
	 * @return 画布上的位置坐标Y
	 */
	public final double getY() {
		return innerEvent.getY();
	}

	/**
	 * 获取在屏幕上的位置坐标X
	 * @return 屏幕上的位置坐标X
	 */
	public final double getScreenX() {
		return innerEvent.getScreenX();
	}

	/**
	 * 获取在屏幕上的位置坐标Y
	 * @return 屏幕上的位置坐标Y
	 */
	public final double getScreenY() {
		return innerEvent.getScreenY();
	}

	/**
	 * 返回事件相对于包含MouseEvent源的场景原点的水平位置。如果节点不在场景中，
	 * 则该值相对于MouseEvent节点最根父节点的boundsInParent。请注意，在
	 * 3D场景中，这表示应用投影变换后的平面坐标。
	 * @return 事件相对于包含MouseEvent源的场景原点的水平位置
	 */
	public final double getSceneX() {
		return innerEvent.getSceneX();
	}

	/**
	 * 返回事件相对于包含MouseEvent源的场景原点的垂直位置。如果节点不在场景中，
	 * 则该值相对于MouseEvent节点最根父节点的boundsInParent。请注意，在
	 * 3D场景中，这表示应用投影变换后的平面坐标。
	 * @return 事件相对于包含MouseEvent源的场景原点的水平位置
	 */
	public final double getSceneY() {
		return innerEvent.getSceneY();
	}

	/**
	 * 哪一个鼠标按钮（如果有）导致了此事件。
	 * @return 其状态更改导致此事件的鼠标按钮
	 */
	public final MouseButton getButton() {
		return innerEvent.getButton();
	}

	/**
	 * 返回与此事件关联的鼠标单击次数。所有鼠标移动的事件的clickCount值均等于0。
	 * 该值随鼠标按下事件的增加而增加，并与所有后续事件的值相同，直到释放鼠标，包括随
	 * 后生成的鼠标单击事件。如果两次后续按下之间的所有事件发生在一个小区域内且时间很
	 * 短（根据本机操作系统配置），则该值将增加到大于1的数字。
	 * @return 与此事件关联的鼠标单击次数
	 */
	public final int getClickCount() {
		return innerEvent.getClickCount();
	}

	/**
	 * 指示自上次按下此事件之前发生的事件以来，鼠标光标是否停留在系统提供的滞后区域。
	 * <P>
	 * 如果在节点上同时按下和释放鼠标，则会为节点生成单击事件，而不考虑鼠标在按下和释
	 * 放之间的移动。如果节点希望在简单单击和鼠标拖动时做出不同的反应，则应使用系统提
	 * 供的短距离阈值来决定单击和拖动（用户通常在单击过程中执行无意的微小移动）。通过
	 * 忽略所有拖动（使用此方法返回true）和忽略所有单击（使用此方法返回false），可
	 * 以很容易地实现这一点。
	 * @return 如果自上次按下此事件之前发生的事件以来，没有显著的鼠标移动（超出系统滞后区域），则返回true。
	 */
	public final boolean isStillSincePress() {
		return innerEvent.isStillSincePress();
	}

	/**
	 * 此事件的Shift键是否按下。
	 * @return 如果此事件的Shift键按下，则返回true 
	 */
	public final boolean isShiftDown() {
		return innerEvent.isShiftDown();
	}

	/**
	 * 此事件的Ctrl键是否按下。
	 * @return 如果此事件的Ctrl键按下，则返回true 
	 */
	public final boolean isControlDown() {
		return innerEvent.isControlDown();
	}

	/**
	 * 此事件的Alt键是否按下。
	 * @return 如果此事件的Alt键按下，则返回true 
	 */
	public final boolean isAltDown() {
		return innerEvent.isAltDown();
	}

	/**
	 * 此事件的Meta键是否按下。
	 * @return 如果此事件的Meta键按下，则返回true 
	 */
	public final boolean isMetaDown() {
		return innerEvent.isMetaDown();
	}

	/**
	 * 返回主机平台公用快捷键是否在此事件中关闭。此常用快捷键是一个修改器键，
	 * 常用于主机平台上的快捷方式。例如，它是Windows上的控件和Mac上的meta（命令键）。
	 * @return 如果快捷键已关闭，则为true，否则为false
	 */
	public final boolean isShortcutDown() {
		return innerEvent.isShortcutDown();
	}

	/**
	 * 如果此鼠标事件是平台的弹出菜单触发事件，则返回true。
	 * <p><b>注意</b>: 弹出菜单在不同系统上的触发方式不同。因此，popupTrigger应在
	 * onMousePressed和MouseRelease中进行检查，以确保跨平台功能正常。
	 * @return 如果此鼠标事件是平台的弹出菜单触发事件，则返回true
	 */
	public final boolean isPopupTrigger() {
		return innerEvent.isPopupTrigger();
	}

	/**
	 * 如果当前按下主按钮（按钮1，通常为左侧），则返回true。
	 * <p><b>注意</b>: 这与getButton()方法不同，getButton()方法指示哪个按钮
	 * 按下导致了此事件，而此方法指示是否按下了此按钮。
	 * @return 如果当前按下主按钮（按钮1，通常为左侧），则返回true 
	 */
	public final boolean isPrimaryButtonDown() {
		return innerEvent.isPrimaryButtonDown();
	}

	/**
	 * 如果当前按下鼠标从按钮（按钮3，通常为右侧），则返回true。
	 * <p><b>注意</b>: 这与getButton()方法不同，getButton()方法指示哪个按钮
	 * 按下导致了此事件，而此方法指示是否按下了此按钮。
	 * @return 如果当前按下鼠标从按钮（按钮3，通常为右侧），则返回true 
	 */
	public final boolean isSecondaryButtonDown() {
		return innerEvent.isSecondaryButtonDown();
	}

	/**
	 * 如果当前按下鼠标中间按钮（按钮2），则返回true。
	 * <p><b>注意</b>: 这与getButton()方法不同，getButton()方法指示哪个按钮
	 * 按下导致了此事件，而此方法指示是否按下了此按钮。
	 * @return 如果当前按下鼠标中间按钮（按钮2），则返回true 
	 */
	public final boolean isMiddleButtonDown() {
		return innerEvent.isMiddleButtonDown();
	}
	
}
