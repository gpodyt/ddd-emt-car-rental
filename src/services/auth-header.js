export default function authHeader() {
    const user = JSON.parse(localStorage.getItem('user'));
    const jwt = localStorage.getItem('JWT');

    if (user && jwt) {
        return { Authorization: jwt };
    } else {
        return {};
    }
}