package main

import "github.com/getlantern/systray"

func setupTray() {
	systray.Run(onReady, onExit)
}

func onReady() {
	systray.SetIcon(Icon)
	systray.SetTooltip("Last Update")
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
