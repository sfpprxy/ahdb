import {eid, getAccount, getBaseUrl} from "./common.js";


window.onload = function () {
    console.debug("item page on load");
    const url = new URL(window.location.toString());
    let accountId = getAccount();
    const item = url.searchParams.get("item");

    if (item) {
        console.debug("accountId", accountId, "item", item);
        axios.get(getBaseUrl() + '/item-stats?account=' + accountId + '&item=' + item)
            .then(function (response) {
                console.debug(response);
                const is = response.data;
                console.debug('itemStats', is);
                console.debug('is.itemDesc.name', is.itemDesc.name);
                eid('itemName').textContent = (is.itemDesc.name)

                const mt = $("#marketTable tbody");

                // todo fix timezone issue
                const at = is.at.slice(0, 10) + ' ' + is.at.slice(11, 16);
                const marketStr = calc(is.currentMarket);

                mt.append(`<tr>
                             <td>最近扫描</td>
                             <td>${marketStr}</td>
                           </tr>`);

                console.debug(is.dailyStats.length);

                is.dailyStats.forEach(s => {
                    console.debug(s.day);
                    console.debug(s.avgMarket);

                    let day = s.day.slice(0, 10);
                    let marketStr = calc(s.avgMarket);
                    mt.append(`<tr>
                                 <td>${day}</td>
                                 <td>${marketStr}</td>
                               </tr>`);
                });


            })
            .catch(function (error) {
                console.error(error);
            });
    }
};

function calc(market) {
    if (market > 10000) {
        market = (market / 10000) + ' g'
    } else if (market > 100) {
        market = (market / 100) + ' s'
    }
    return market
}
