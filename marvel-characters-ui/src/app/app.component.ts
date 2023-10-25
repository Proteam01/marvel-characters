import { Component, OnInit } from '@angular/core';
import { MarvelService } from './marvel-service.service';
import { Character, CharacterDetail } from './character';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'marvel-characters-ui';

  characters: Character[];

  characterDetail: CharacterDetail;

  constructor(private marvelService: MarvelService) {
    this.characters = [];
    this.characterDetail = {};
  }

  ngOnInit(): void {
    this.marvelService.getHeroes().subscribe(response => this.characters = response);
  }

  showCharacterDetails(id: any) {
    this.marvelService.getHeroDetail(id).subscribe((response) => {
      this.characterDetail = response;
      this.showBlackout(true);
      this.showUrls();
    });
  }

  showBlackout(show: any) {
    if (show) {
      const blackout = document.createElement('div');
      blackout.id = "blackout"
      blackout.onclick = () => {
        this.closeDetails();
      };
      blackout.style.display = 'block';
      document.body.append(blackout);
    } else {
      const blackout = document.getElementById('blackout');
      if (blackout) {
        blackout.remove();
      }
    }
  }

  closeDetails(){
    let blackout = document.getElementById('blackout');
    blackout?.remove();
    let urlDialog = window.document.getElementById('character-detail');
    if(urlDialog != null){
      urlDialog.style.display='none';
    }
  }

  showUrls(){
    let urlDialog = window.document.getElementById('character-detail');
    if(urlDialog != null){
      urlDialog.remove();
      window.document.body.append(urlDialog);
      urlDialog.style.display='block';
    }
  }


}
