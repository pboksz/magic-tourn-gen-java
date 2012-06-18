function dropCheck(who)
{
   return confirm("Do you really want to drop [ " + who + " ] from the tournament?");
}

function addRounds(numPlayers)
{
   if ((numPlayers >= 4) && numPlayers <= 8)
   {
      clearRounds()
   }
   if ((numPlayers > 8) && (numPlayers <= 16))
   {
      clearRounds();
      addOptions(1);
   }
   if ((numPlayers > 16) && (numPlayers <= 32))
   {
      clearRounds();
      addOptions(2);
   }
   if ((numPlayers > 32) && (numPlayers <= 64))
   {
      clearRounds();
      addOptions(3);
   }
   if ((numPlayers > 64) && (numPlayers <= 128))
   {
      clearRounds();
      addOptions(4);
   }
   if ((numPlayers > 128) && (numPlayers <= 256))
   {
      clearRounds();
      addOptions(5);
   }
   if ((numPlayers > 256) && (numPlayers <= 512))
   {
      clearRounds();
      addOptions(6);
   }
}

function clearRounds()
{
   document.settings.howManyRounds.options.length = 1;
}

function addOptions(num)
{
   for (var i = 1; i < (num + 1); i++)
   {
      document.settings.howManyRounds.options[i] = new Option(i + 3);
   }
}

function verifyValue(field, maxNum)
{
   //if there is an error do a red outline
   if (field.value > maxNum)
   {
      field.style.borderColor = "#ff8888";
      field.style.mozBoxShadow = "0 0 0.25em #ff8888";
      field.style.webkitBoxShadow = "0 0 0.25em #ff8888";
      field.style.boxShadow = "0 0 0.25em #ff8888";
   }
   //else do a blue one
   else
   {
      field.style.borderColor = "#b2d1ff";
      field.style.mozBoxShadow = "0 0 0.25em #b2d1ff";
      field.style.webkitBoxShadow = "0 0 0.25em #b2d1ff";
      field.style.boxShadow = "0 0 0.25em #b2d1ff";
   }
}

//TODO testing this to check that the sum of the two are valid, should highlight the incorrect rows
function verifyValues(bestOf, maxWins) {
   isValid = true;
   var wins = document.show.wins;
   var losses = document.show.losses;

   for(var i = 0; i < wins.length; i++) {
      //if something is blank, return error
      //CASE: nothing is filled in for values
      if((wins[i].value == "") || (losses[i].value == "")) {
         isValid = setRowError(i);
      }

      //get the values of wins and losses
      var win = parseInt(wins[i].value);
      var loss = parseInt(losses[i].value);

      //skip over rows that have byes
      if((win != -1) && (loss != -1)){
         //if values are greater than maxWins, return error
         //CASE: wins is 4 and maxWins is 2 in a bestOf 3 tournament
         if((win > maxWins) || (loss > maxWins)) {
            isValid = setRowError(i);
         }
         else if(win + loss > bestOf) {
            isValid = setRowError(i);
         }
         else if(!((win == maxWins) || (loss == maxWins))) {
            isValid = setRowError(i);
         }
      }
   }
   return isValid;
}

//helper function to change row visual
function setRowError(rowNum) {
   var table = document.getElementById("showtable");
   var row = table.tBodies[0].rows[rowNum];

//   for(var i=0; i<row.cells.length; i++) {
//      var cell = row.cells[i];
//      cell.style.borderColor = "#ff8888";
//   }

   //TODO need to move this visual above the individual cells
//   row.style.mozBoxShadow = "0 0 0.25em #b2d1ff";
//   row.style.webkitBoxShadow = " 0 0 0.25em #b2d1ff";
//   row.style.boxShadow = "0 0 0.25em #b2d1ff";

   document.getElementById("sumerror").style.display = "block";
   return false;
}
