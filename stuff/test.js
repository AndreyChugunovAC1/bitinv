/* 
Get value from cookie

const myCookie = getCookieLikeValue("alla=sd;asda=sd;MyCookie=123;asd=2;;2=2;2+2=5;", "MyCookie");
console.log(myCookie);

function getCookieLikeValue(str, name) {
  const regex = new RegExp(`${name}=([^;]*);`);
  const match = str.match(regex);
  return match ? decodeURIComponent(match[1]) : null;
} */

log = console.log