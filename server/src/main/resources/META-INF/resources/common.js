function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

export function getBaseUrl() {
    return 'http://localhost:9999/ahdb';
    // return 'http://123.206.124.78:9999/ahdb';
}

export function getAccount() {
    const url = new URL(window.location.toString());
    return url.searchParams.get("account");
}

export function eid(id) {
    return document.getElementById(id);
}

const searchBox = eid('searchBox');
let itemList = eid('itemList');

async function submitInputJob() {
    let lastVal = searchBox.value;
    while (true) {
        searchBox.value = searchBox.value.trim();
        await sleep(500);
        if (lastVal !== searchBox.value) {
            lastVal = searchBox.value;
            axios.get(getBaseUrl() + '/find-items' + '?item=' + searchBox.value)
                .then(function (response) {
                    const items = response.data;

                    for (let i = itemList.options.length - 1; i >= 0; i--) {
                        itemList.options[i].remove();
                    }

                    items.forEach(item => {
                        const option = document.createElement('option');
                        option.value = item.name;
                        itemList.appendChild(option);
                    });
                    for (const item of itemList.options) {
                        console.debug('inn item', item.value);
                    }
                }).catch(function (error) {
                    // console.log(error);
                });
        }
    }
}
submitInputJob().catch();

export function searchItem() {
    console.debug(searchBox);

    let account = getAccount();
    if (!account) {
        account = ''
    }
    // window.location.href = getBaseUrl() + '/item' + '?account=' + account + '&item=' + searchBox.value;
    window.location.href = 'item.html' + '?account=' + account + '&item=' + searchBox.value;
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
