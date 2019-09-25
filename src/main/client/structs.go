package main

type ValuableData struct {
	Chars         string `json:"chars"`
	AuctionDBScan string `json:"auctionDBScan"`
}

type ValuableDataByAccount struct {
	AccountId    string       `json:"accountId"`
	ValuableData ValuableData `json:"valuableData"`
}
