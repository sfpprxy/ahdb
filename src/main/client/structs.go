package main

type ValuableData struct {
	Chars         string `json:"chars"`
	AuctionDBScan string `json:"auctionDBScan"`
}

type ValuableDataByAccount struct {
	Type         string       `json:"type"`
	AccountId    string       `json:"accountId"`
	ValuableData ValuableData `json:"valuableData"`
}

type Config struct {
	WowPath    string `json:"wowPath"`
	LastUpload string `json:"lastUpload"`
}
