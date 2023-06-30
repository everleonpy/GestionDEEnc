package gui;

import java.awt.Color;
import java.awt.Font;

public class AppLookAndFeel  {
	
	private boolean lightBackground;
	private boolean bckgndImage;
	private Color headerBg;
	private Color headerFg;
	private Color menuBg;
	private Color menuFg;
	private Color selMenuBg;
	private Color selMenuFg;
	private Color bodyBg;
	private Color bodyFg;
	private Color footerBg;
	private Color footerFg;
	private Color txtFieldBg;
	private Color txtFieldFg;
	private Color msgWindowBg;
	private Color msgWindowFg;
	private Color errWindowBg;
	private Color errWindowFg;
	private Color toolbarBg;
	private Color toolbarFg;
	private Color toolbarButtonBg;
	private Color toolbarButtonFg;
	private Color menuButtonBg;
	private Color menuButtonFg;
	//
	private Color primaryBg;
	private Color primaryFg;
	private Color secondaryBg;
	private Color secondaryFg;
	private Color successBg;
	private Color successFg;
	private Color infoBg;
	private Color infoFg;
	private Color warningBg;
	private Color warningFg;
	private Color helpBg;
	private Color helpFg;
	private Color dangerBg;
	private Color dangerFg;
	//
	private Font headerFont1;
	private Font headerFont2;
	private Font headerFont3;
	private Font regularFont;
	private Font smallFont;
	private Font miniFont;
	private Font microFont;
	
	public AppLookAndFeel ( String lookAndFeel ) {
		if (lookAndFeel.equalsIgnoreCase("BLUE-SKY")) {
			this.setLightBackground(true);
			this.setBckgndImage(true);
			this.setHeaderBg(BlueSky.HEADER_BG);
			this.setHeaderFg(BlueSky.HEADER_FG);
			this.setMenuBg(BlueSky.MENU_BG);
			this.setMenuFg(BlueSky.MENU_FG);
			this.setSelMenuBg(BlueSky.SEL_MENU_BG);
			this.setSelMenuFg(BlueSky.SEL_MENU_FG);
			this.setBodyBg(BlueSky.BODY_BG);
			this.setBodyFg(BlueSky.BODY_FG);
			this.setFooterBg(BlueSky.FOOTER_BG);
			this.setFooterFg(BlueSky.FOOTER_FG);
			this.setTxtFieldBg(BlueSky.TXTFIELD_BG);
			this.setTxtFieldFg(BlueSky.TXTFIELD_FG);
			this.setMsgWindowBg(BlueSky.MSGWIN_BG);
			this.setMsgWindowFg(BlueSky.MSGWIN_FG);
			this.setErrWindowBg(BlueSky.ERRWIN_BG);
			this.setErrWindowFg(BlueSky.ERRWIN_FG);
			this.setToolbarButtonBg(BlueSky.TBARBTN_BG);
			this.setToolbarButtonFg(BlueSky.TBARBTN_FG);
			this.setToolbarBg(BlueSky.TBARBTN_BG);
			this.setToolbarFg(BlueSky.TBARBTN_FG);
			this.setMenuButtonBg(BlueSky.MENUBTN_BG);
			this.setMenuButtonFg(BlueSky.MENUBTN_FG);
			//
			this.setPrimaryBg(BlueSky.PRIMARY_BG);
			this.setPrimaryFg(BlueSky.PRIMARY_FG);
			this.setSecondaryBg(BlueSky.SECONDARY_BG);
			this.setSecondaryFg(BlueSky.SECONDARY_FG);
			this.setSuccessBg(BlueSky.SUCCESS_BG);
			this.setSuccessFg(BlueSky.SUCCESS_FG);
			this.setInfoBg(BlueSky.INFO_BG);
			this.setInfoFg(BlueSky.INFO_FG);
			this.setWarningBg(BlueSky.WARNING_BG);
			this.setWarningFg(BlueSky.WARNING_FG);
			this.setDangerBg(BlueSky.DANGER_BG);
			this.setDangerFg(BlueSky.DANGER_FG);
			//
			this.setHeaderFont1(BlueSky.HEADER1_FONT);
			this.setHeaderFont2(BlueSky.HEADER2_FONT);
			this.setHeaderFont3(BlueSky.HEADER3_FONT);
			this.setRegularFont(BlueSky.REGULAR_FONT);
			this.setSmallFont(BlueSky.SMALL_FONT);
			this.setMiniFont(BlueSky.MINI_FONT);
			this.setMicroFont(BlueSky.MICRO_FONT);
		}
		//
		if (lookAndFeel.equalsIgnoreCase("GREEN-FOREST")) {
			this.setLightBackground(false);
			this.setBckgndImage(false);
			this.setHeaderBg(GreenForest.HEADER_BG);
			this.setHeaderFg(GreenForest.HEADER_FG);
			this.setBodyBg(GreenForest.BODY_BG);
			this.setBodyFg(GreenForest.BODY_FG);
			this.setFooterBg(GreenForest.FOOTER_BG);
			this.setFooterFg(GreenForest.FOOTER_FG);
			this.setTxtFieldBg(GreenForest.TXTFIELD_BG);
			this.setTxtFieldFg(GreenForest.TXTFIELD_FG);
			this.setMsgWindowBg(GreenForest.MSGWIN_BG);
			this.setMsgWindowFg(GreenForest.MSGWIN_FG);
			this.setErrWindowBg(GreenForest.ERRWIN_BG);
			this.setErrWindowFg(GreenForest.ERRWIN_FG);
			this.setToolbarButtonBg(GreenForest.TBARBTN_BG);
			this.setToolbarButtonFg(GreenForest.TBARBTN_FG);
			this.setMenuButtonBg(GreenForest.MENUBTN_BG);
			this.setMenuButtonFg(GreenForest.MENUBTN_FG);
			//
			this.setPrimaryBg(BlueSky.PRIMARY_BG);
			this.setPrimaryFg(BlueSky.PRIMARY_FG);
			this.setSecondaryBg(BlueSky.SECONDARY_BG);
			this.setSecondaryFg(BlueSky.SECONDARY_FG);
			this.setSuccessBg(BlueSky.SUCCESS_BG);
			this.setSuccessFg(BlueSky.SUCCESS_FG);
			this.setInfoBg(BlueSky.INFO_BG);
			this.setInfoFg(BlueSky.INFO_FG);
			this.setWarningBg(BlueSky.WARNING_BG);
			this.setWarningFg(BlueSky.WARNING_FG);
			this.setDangerBg(BlueSky.DANGER_BG);
			this.setDangerFg(BlueSky.DANGER_FG);
			//
			this.setHeaderFont1(GreenForest.HEADER1_FONT);
			this.setHeaderFont2(GreenForest.HEADER2_FONT);
			this.setHeaderFont3(GreenForest.HEADER3_FONT);
			this.setRegularFont(GreenForest.REGULAR_FONT);
			this.setSmallFont(GreenForest.SMALL_FONT);
			this.setMiniFont(GreenForest.MINI_FONT);
			this.setMicroFont(GreenForest.MICRO_FONT);
		}
		
	}

	public boolean isLightBackground() {
		return lightBackground;
	}
	public void setLightBackground(boolean lightBackground) {
		this.lightBackground = lightBackground;
	}
	public boolean isBckgndImage() {
		return bckgndImage;
	}
	public void setBckgndImage(boolean bckgndImage) {
		this.bckgndImage = bckgndImage;
	}
	public Color getHeaderBg() {
		return headerBg;
	}
	public void setHeaderBg(Color headerBg) {
		this.headerBg = headerBg;
	}
	public Color getHeaderFg() {
		return headerFg;
	}
	public void setHeaderFg(Color headerFg) {
		this.headerFg = headerFg;
	}
	public Color getMenuBg() {
		return menuBg;
	}

	public void setMenuBg(Color menuBg) {
		this.menuBg = menuBg;
	}

	public Color getMenuFg() {
		return menuFg;
	}

	public void setMenuFg(Color menuFg) {
		this.menuFg = menuFg;
	}

	public Color getSelMenuBg() {
		return selMenuBg;
	}

	public void setSelMenuBg(Color selMenuBg) {
		this.selMenuBg = selMenuBg;
	}

	public Color getSelMenuFg() {
		return selMenuFg;
	}

	public void setSelMenuFg(Color selMenuFg) {
		this.selMenuFg = selMenuFg;
	}

	public Color getBodyBg() {
		return bodyBg;
	}
	public void setBodyBg(Color bodyBg) {
		this.bodyBg = bodyBg;
	}
	public Color getBodyFg() {
		return bodyFg;
	}
	public void setBodyFg(Color bodyFg) {
		this.bodyFg = bodyFg;
	}
	public Color getFooterBg() {
		return footerBg;
	}
	public void setFooterBg(Color footerBg) {
		this.footerBg = footerBg;
	}
	public Color getFooterFg() {
		return footerFg;
	}
	public void setFooterFg(Color footerFg) {
		this.footerFg = footerFg;
	}
	public Color getTxtFieldBg() {
		return txtFieldBg;
	}

	public void setTxtFieldBg(Color txtFieldBg) {
		this.txtFieldBg = txtFieldBg;
	}

	public Color getTxtFieldFg() {
		return txtFieldFg;
	}

	public void setTxtFieldFg(Color txtFieldFg) {
		this.txtFieldFg = txtFieldFg;
	}

	public Color getMsgWindowBg() {
		return msgWindowBg;
	}
	public void setMsgWindowBg(Color msgWindowBg) {
		this.msgWindowBg = msgWindowBg;
	}
	public Color getMsgWindowFg() {
		return msgWindowFg;
	}
	public void setMsgWindowFg(Color msgWindowFg) {
		this.msgWindowFg = msgWindowFg;
	}
	public Color getErrWindowBg() {
		return errWindowBg;
	}
	public void setErrWindowBg(Color errWindowBg) {
		this.errWindowBg = errWindowBg;
	}
	public Color getErrWindowFg() {
		return errWindowFg;
	}
	public void setErrWindowFg(Color errWindowFg) {
		this.errWindowFg = errWindowFg;
	}
	public Color getToolbarButtonBg() {
		return toolbarButtonBg;
	}
	public void setToolbarButtonBg(Color toolbarButtonBg) {
		this.toolbarButtonBg = toolbarButtonBg;
	}
	public Color getToolbarButtonFg() {
		return toolbarButtonFg;
	}
	public void setToolbarButtonFg(Color toolbarButtonFg) {
		this.toolbarButtonFg = toolbarButtonFg;
	}
	public Color getToolbarBg() {
		return toolbarBg;
	}
	public void setToolbarBg(Color toolbarBg) {
		this.toolbarBg = toolbarBg;
	}
	public Color getToolbarFg() {
		return toolbarFg;
	}
	public void setToolbarFg(Color toolbarFg) {
		this.toolbarFg = toolbarFg;
	}
	public Color getMenuButtonBg() {
		return menuButtonBg;
	}
	public void setMenuButtonBg(Color menuButtonBg) {
		this.menuButtonBg = menuButtonBg;
	}
	public Color getMenuButtonFg() {
		return menuButtonFg;
	}
	public void setMenuButtonFg(Color menuButtonFg) {
		this.menuButtonFg = menuButtonFg;
	}
		
	public Color getPrimaryBg() {
		return primaryBg;
	}

	public void setPrimaryBg(Color primaryBg) {
		this.primaryBg = primaryBg;
	}

	public Color getPrimaryFg() {
		return primaryFg;
	}

	public void setPrimaryFg(Color primaryFg) {
		this.primaryFg = primaryFg;
	}

	public Color getSecondaryBg() {
		return secondaryBg;
	}

	public void setSecondaryBg(Color secondaryBg) {
		this.secondaryBg = secondaryBg;
	}

	public Color getSecondaryFg() {
		return secondaryFg;
	}

	public void setSecondaryFg(Color secondaryFg) {
		this.secondaryFg = secondaryFg;
	}

	public Color getSuccessBg() {
		return successBg;
	}

	public void setSuccessBg(Color successBg) {
		this.successBg = successBg;
	}

	public Color getSuccessFg() {
		return successFg;
	}

	public void setSuccessFg(Color successFg) {
		this.successFg = successFg;
	}

	public Color getInfoBg() {
		return infoBg;
	}

	public void setInfoBg(Color infoBg) {
		this.infoBg = infoBg;
	}

	public Color getInfoFg() {
		return infoFg;
	}

	public void setInfoFg(Color infoFg) {
		this.infoFg = infoFg;
	}

	public Color getWarningBg() {
		return warningBg;
	}

	public void setWarningBg(Color warningBg) {
		this.warningBg = warningBg;
	}

	public Color getWarningFg() {
		return warningFg;
	}

	public void setWarningFg(Color warningFg) {
		this.warningFg = warningFg;
	}

	public Color getHelpBg() {
		return helpBg;
	}

	public void setHelpBg(Color helpBg) {
		this.helpBg = helpBg;
	}

	public Color getHelpFg() {
		return helpFg;
	}

	public void setHelpFg(Color helpFg) {
		this.helpFg = helpFg;
	}

	public Color getDangerBg() {
		return dangerBg;
	}

	public void setDangerBg(Color dangerBg) {
		this.dangerBg = dangerBg;
	}

	public Color getDangerFg() {
		return dangerFg;
	}

	public void setDangerFg(Color dangerFg) {
		this.dangerFg = dangerFg;
	}

	public Font getHeaderFont1() {
		return headerFont1;
	}
	public void setHeaderFont1(Font headerFont1) {
		this.headerFont1 = headerFont1;
	}
	public Font getHeaderFont2() {
		return headerFont2;
	}
	public void setHeaderFont2(Font headerFont2) {
		this.headerFont2 = headerFont2;
	}
	public Font getHeaderFont3() {
		return headerFont3;
	}
	public void setHeaderFont3(Font headerFont3) {
		this.headerFont3 = headerFont3;
	}
	public Font getRegularFont() {
		return regularFont;
	}
	public void setRegularFont(Font regularFont) {
		this.regularFont = regularFont;
	}
	public Font getSmallFont() {
		return smallFont;
	}
	public void setSmallFont(Font smallFont) {
		this.smallFont = smallFont;
	}
	public Font getMiniFont() {
		return miniFont;
	}
	public void setMiniFont(Font miniFont) {
		this.miniFont = miniFont;
	}
	public Font getMicroFont() {
		return microFont;
	}
	public void setMicroFont(Font microFont) {
		this.microFont = microFont;
	}
}
