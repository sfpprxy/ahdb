
export function getBaseUrl() {
    console.log('getBaseUrl');
    return 'http://123.206.124.78:9999/ahdb';
}

export function getAccount() {
    const url = new URL(window.location.toString());
    let accountId = url.searchParams.get("account");
    return accountId;
}


export function searchItem() {
    const sb = $("#searchBox")[0];
    console.debug(sb);
    let account = getAccount();
    if (!account) {
        account = ''
    }
    window.location.href = getBaseUrl() + '/item' + '?account=' + account + '&item=' + sb.value;
}

window.searchItem = searchItem;
