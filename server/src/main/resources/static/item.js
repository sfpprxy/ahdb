var baseUrl = 'http://123.206.124.78:9999/ahdb';
var accountId;

window.onload = function () {
    console.debug("item page on load");
    var url = new URL(window.location.toString());
    accountId = url.searchParams.get("account");
    var item = url.searchParams.get("item");

    if (accountId && item) {
        console.debug("accountId", accountId, "item", item);
        axios.get(baseUrl + '/item-stats?account=' + accountId + '&item=' + item)
            .then(function (response) {
                console.debug(response);
                var is = response.data;
                console.debug('itemStats', is);
                console.debug('is.itemDesc.name', is.itemDesc.name);
                $("#itemName")[0].textContent = (is.itemDesc.name)

                var mt = $("#marketTable tbody");
                console.log(mt);

                let at = is.at.slice(0, 10) + ' ' + is.at.slice(11, 16);
                let marketStr = calc(is.currentMarket);

                mt.append(`<tr>
                             <td>最近扫描</td>
                             <td>${marketStr}</td>
                           </tr>`);

                console.debug(is.dailyStats.length)

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

function searchItem() {
    var sb = $("#searchBox")[0];
    console.debug(sb);
    window.location.href = baseUrl + '/item' + '?account=' + accountId + '&item=' + sb.value;
}
