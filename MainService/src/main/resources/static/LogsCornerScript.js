let table_body=document.getElementById("table_body");
let row = document.createElement("tr");
row.innerHTML="<td></td><td></td><td></td><td></td>"+
        "<td><div>"+
          "<button class='btn btn-sm btn-danger' onclick='removeTest(this.parentElement.parentElement.parentElement.id)'>Delete</button></div></td>";

let logServiceURL = "/logs";
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
  data.testId = textToUndefined(document.getElementById("testId").value);
  data.userId = textToUndefined(document.getElementById("userId").value);
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
  children[1].innerText=undefinedToText(data.testId);
  children[2].innerText=undefinedToText(data.userId);
  children[3].innerText=undefinedToText(data.branch);

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
  document.getElementById("testId").value="";
  document.getElementById("userId").value="";
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

async function searchLog(){
  let data = formInput();

  let results;

  results = await get(getURL(logServiceURL,data));

  if(results==null||results.length==0){
    return;
  }

  clearLogs();
  clearRows();

  extendLogs(results);
  createRows();

  reset();
}

async function removeLog(id){
  let response = await remove(getURL(logServiceURL,{id:id}));
  if(!response){
    return;
  }
  removeLog(id);
  removeRow(id);
}
