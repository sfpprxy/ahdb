var baseUrl = 'http://123.206.124.78:9999/ahdb';
var accountId;

window.onload = function () {
    var url = new URL(window.location.toString());
    accountId = url.searchParams.get("account");

    if (accountId) {
        console.debug("accountId", accountId);
        axios.get(baseUrl + '/account-stats?account=' + accountId)
            .then(function (response) {
                console.debug(response);
                var nick = response.data.chars;
                console.debug("nick", nick);
                $("#banner")[0].textContent = ("欢迎来到藏宝海湾," + nick + "！!");
            })
            .catch(function (error) {
                console.error(error);
            });
    }
};

function searchItem() {
    var sb = $("#searchBox")[0];
    console.debug(sb);
    window.location.href = baseUrl + '/item' + '?account=' + accountId + '&item='+sb.value;
}
