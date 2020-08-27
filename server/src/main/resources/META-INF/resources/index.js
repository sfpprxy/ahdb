import {eid, getAccount, getBaseUrl} from "./common.js";


window.onload = function () {
    console.debug("index onload");
    let accountId = getAccount();

    if (accountId) {
        console.debug("accountId", accountId);
        axios.get(getBaseUrl() + '/account-stats?account=' + accountId)
            .then(function (response) {
                console.debug(response);
                const nick = response.data.chars;
                console.debug("nick", nick);
                eid('banner').textContent = ("欢迎来到藏宝海湾," + nick + "！");
            })
            .catch(function (error) {
                console.error(error);
            });
    }
};
