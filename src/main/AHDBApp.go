package main

import (
	_ "github.com/andlabs/ui/winmanifest"
	"github.com/getlantern/systray"
	"github.com/getlantern/systray/example/icon"
	_ "log"
)

func main() {
	systray.Run(onReady, onExit)

	//err := ui.Main(func() {
	//	window := ui.NewWindow("Auction House Database App", 500, 500, true)
	//	box := ui.NewVerticalBox()
	//
	//	chooseButton := ui.NewButton("选择文件")
	//	box.Append(chooseButton, true)
	//
	//
	//	window.SetChild(box)
	//	window.SetMargined(true)
	//	window.OnClosing(func(*ui.Window) bool {
	//		ui.Quit()
	//		return true
	//	})
	//	ui.OnShouldQuit(func() bool {
	//		window.Destroy()
	//		return true
	//	})
	//	window.Show()
	//})
	//if err != nil {
	//	log.Fatalln(err)
	//}
}

func onReady() {
	systray.SetIcon(icon.Data)
	systray.SetTitle("Awesome App")
	systray.SetTooltip("Pretty awesome超级棒")
	mQuit := systray.AddMenuItem("Quit", "Quit the whole app")

	// Sets the icon of a menu item. Only available on Mac.
	mQuit.SetIcon(icon.Data)
}

func onExit() {
	// clean up here
}

