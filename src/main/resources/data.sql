 DROP TABLE IF EXISTS gamemove;
 DROP TABLE IF EXISTS game;
 
CREATE TABLE game (
  id IDENTITY PRIMARY KEY,
  status VARCHAR(250) NOT NULL,
  wonby VARCHAR(250)
);

CREATE TABLE gamemove (
  id IDENTITY PRIMARY KEY,
  gameid INT NOT NULL,
  player VARCHAR(250) NOT NULL,
  addend INT NOT NULL,
  result INT NOT NULL
);

ALTER TABLE gamemove
ADD FOREIGN KEY (gameid) 
REFERENCES game(id)