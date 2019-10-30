export function getBaseUrl() {
    console.log('getBaseUrl');
    return 'http://123.206.124.78:9999/ahdb';
}

export function getAccount() {
    const url = new URL(window.location.toString());
    return url.searchParams.get("account");
}

export function searchItem() {
    const sb = eid('searchBox');
    console.debug(sb);

    let account = getAccount();
    if (!account) {
        account = ''
    }
    window.location.href = getBaseUrl() + '/item' + '?account=' + account + '&item=' + sb.value;
}

eid('searchBtn').addEventListener('click', function (e) {
    console.debug('onclick');
    searchItem();
});

eid('searchForm').addEventListener('keypress', function (e) {
    if (e.key === 'Enter') {
        console.debug('keypress');
        e.preventDefault();
        searchItem();
    }
});

export function eid(id) {
    return document.getElementById(id);
}
