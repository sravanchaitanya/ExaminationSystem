let table_body=document.getElementById("table_body");
let row = document.createElement("tr");
row.innerHTML="<td></td><td></td><td></td><td></td>"+
        "<td> <div class='d-flex justify-content-around'>"+
          "<button class='btn btn-sm btn-primary' onclick='editUser(this.parentElement.parentElement.parentElement.id)'>Edit</button>"+
          "<button class='btn btn-sm btn-danger' onclick='removeUser(this.parentElement.parentElement.parentElement.id)'>Delete</button></div></td>";

let userServiceURL = "/users";
let logs = new Object();
let editFlag = false;

function setEditFlag(){
  editFlag=true;
}

function resetEditFlag(){
  editFlag=false;
}

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
  data.id = textToUndefined(document.getElementById("id").value);
  data.password = textToUndefined(document.getElementById("password").value);
  data.branch = textToUndefined(document.getElementById("branch").value);
  data.role = textToUndefined(document.getElementById("role").value);
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
  children[1].innerText=undefinedToText(data.password);
  children[2].innerText=undefinedToText(data.branch);
  children[3].innerText=undefinedToText(data.role);

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
  if(formInput().length==0){
    clearLogs();
    clearRows();
    return;
  }

  id.value="";
  id.disabled=false;
  id.readonly=false;
  password.value="";
  branch.selectedIndex=0;
  role.selectedIndex=0;

  resetEditFlag();
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

async function searchUser(){
  let data = formInput();

  let results;
  console.log(data);
  console.log(data.length);

  results = await get(getURL(userServiceURL,data));
  console.log(results);

  if(results==null||results.length==0){
    return;
  }

  clearLogs();
  clearRows();

  extendLogs(results);
  createRows();

  reset();
}

async function addUser(){
  let data = formInput();

  if(data.id===undefined){
    alert("Username is required.");
    return ;
  }

  if(editFlag){
    let response = await post(userServiceURL,data);
    if(!response){
      return;
    }
    let results = await get(getURL(userServiceURL,{id:data.id}));
    removeLog(data.id);
    appendLog(results[0]);

    removeRow(data.id);
    prependRow(data.id);

    reset();
  }
  else{
    let results = await get(getURL(userServiceURL,{id:data.id}));
    if(results.length!=0){
      alert("User Already Exists.");
      return;
    }
    let response = await post(userServiceURL,data);
    console.log(response);
    if(!response){
      return;
    }
    appendLog(data);
    prependRow(data.id);
    reset();
  }

}

function editUser(id){
  let data = logs[id];

  let userid = document.getElementById("id");
  userid.value=data.id;
  userid.disabled=true;
  userid.readonly=true;

  document.getElementById("password").value=undefinedToText(data.password);
  document.getElementById("branch").value=undefinedToText(data.branch);
  document.getElementById("role").value=undefinedToText(data.role);

  setEditFlag()
}

async function removeUser(id){
  let response = await remove(getURL(userServiceURL,{id:id}));
  if(!response){
    return;
  }
  removeLog(id);
  removeRow(id);
}
