import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError  } from 'rxjs';
import { Character, CharacterDetail } from './character';
import { map, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class MarvelService {

  constructor( private http: HttpClient ) { }

  getHeroes() : Observable<Character[]> {
    return this.http.get<Character[]>('characters');
  }

  getHeroDetail(id:any): Observable<CharacterDetail>{
    return this.http.get<CharacterDetail>(`characters/${id}`);
  }

}
