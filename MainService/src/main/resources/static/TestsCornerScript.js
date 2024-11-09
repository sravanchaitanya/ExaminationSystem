let table_body=document.getElementById("table_body");
let row = document.createElement("tr");
row.innerHTML="<td></td><td></td><td></td>"+
        "<td><div>"+
          "<button class='btn btn-sm btn-danger' onclick='removeTest(this.parentElement.parentElement.parentElement.id)'>Delete</button></div></td>";

let testServiceURL = "/tests";
let logs = new Object();

function textToUndefined(text){
  if(text==""){
    return undefined;
  }
  return text;
}

function undefinedToText(text){
  if(text===undefined||text==null){
    return "";
  }
  return text;
}

function formInput(){
  let data = new Object();
  data.name = textToUndefined(document.getElementById("name").value);
  data.branch = textToUndefined(document.getElementById("branch").value);
  return data;
}

function appendLog(log){
  logs[log.id]=log;
}

function extendLogs(data){
  for(let log of data){
    appendLog(log);
  }
}

function removeLog(id){
  delete logs[id];
}

function clearLogs(){
  for(let i in logs){
    delete logs[i];
  }
}

function createRow(id){
  let data = logs[id];

  let curr_row = row.cloneNode(true);

  let children = curr_row.children;

  children[0].innerText=undefinedToText(data.id);
  children[1].innerText=undefinedToText(data.name);
  children[2].innerText=undefinedToText(data.branch);

  return curr_row;
}

function prependRow(id){
  let curr_row = createRow(id);
  curr_row.id=id;
  document.getElementById("table_body").prepend(curr_row);
}

function updateRow(id){
  let curr_row = createRow(id);
  document.getElementById(id).innerHTML=curr_row.innerHTML;
}

function createRows(){
  for(let id in logs){
    prependRow(id);
  }
}

function removeRow(id){
  document.getElementById(id).remove();
}

function clearRows(){
  document.getElementById("table_body").innerHTML="";
}

function reset(){
  document.getElementById("name").value="";
  document.getElementById("branch").selectedIndex=0;
}

async function get(url){
    try{
      let response = await fetch(url);
      return await response.json();
    }
    catch(error){
      return new Array();
    }
}

async function post(url,body){
  try{
    let response = await fetch(url,{
      method:"POST",
      headers:{"Content-Type":"application/json"},
      body:JSON.stringify(body)
    });
    return response.ok;
  }
  catch(error){
    return false;
  }
}

async function put(url,body){
  try{
    let response = await fetch(url,{
      method:"PUT",
      headers:{"Content-Type":"application/json"},
      body:JSON.stringify(body)
    });
    return response.ok;
  }
  catch(error){
    return false;
  }
}

async function remove(url){
  try{
    let response = await fetch(url,{
      method:"DELETE"
    });
    return response.ok
  }
  catch(error){
    return false;
  }
}

function getURL(relativeURL,data){
  const params = new URLSearchParams();
  for(let i in data){
    if(data[i]){
      params.append(i,data[i]);
    }
  }
  return relativeURL+"?"+params.toString();
}

async function searchTest(){
  let data = formInput();

  let results;

  results = await get(getURL(testServiceURL,data));

  if(results==null||results.length==0){
    return;
  }

  clearLogs();
  clearRows();

  extendLogs(results);
  createRows();

  reset();
}

async function addTest(){
  let data = formInput();

  if(data.name===undefined){
    alert("Testname is required.");
    return ;
  }

  let results = await get(getURL(testServiceURL,data));
  console.log(results);
  if(results.length!=0){
    alert("Test Already Exists.");
    return;
  }
  let response = await post(testServiceURL,data);
  if(!response){
    return;
  }
  let callback = await get(getURL(testServiceURL,data));
  appendLog(callback[0]);
  prependRow(callback[0].id);
  reset();

}

async function removeTest(id){
  let response = await remove(getURL(testServiceURL,{id:id}));
  if(!response){
    return;
  }
  removeLog(id);
  removeRow(id);
}

