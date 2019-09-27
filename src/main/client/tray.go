package main

import (
	"github.com/getlantern/systray"
	"time"
)

func setupTray() {
	systray.Run(onReady, onExit)
}

func trayUpdater() {
	icon := IconWin
	iconbg := IconBgWin
	if isOnMac() {
		icon = IconMac
		iconbg = IconBgMac
	}

	for {
		var blink int64
		diff := time.Now().Sub(lastUpload)
		if diff.Minutes() > 60 {
			blink = 300
		} else if diff.Minutes() > 30 {
			blink = 800
		} else {
			blink = 0
		}

		systray.SetTooltip("最近上传：" + lastUpload.Format(timeLayout))
		if blink != 0 {
			systray.SetIcon(icon)
			time.Sleep(time.Duration(blink) * time.Millisecond)
			systray.SetIcon(iconbg)
			time.Sleep(time.Duration(blink) * time.Millisecond)
		} else {
			systray.SetIcon(icon)
			time.Sleep(time.Duration(1000) * time.Millisecond)
		}
	}
}

func onReady() {
	go trayUpdater()
	mMain := systray.AddMenuItem("主界面", "1")
	mQuit := systray.AddMenuItem("退出", "2")

	go func() {
		for {
			select {
			case <-mMain.ClickedCh:
				log.Debug("mMain.ClickedCh")
				mainwin.Show()
			case <-mQuit.ClickedCh:
				log.Debug("mQuit.ClickedCh")
				systray.Quit()
				return
			}
		}
	}()
}

func onExit() {
	// clean up here
}
