import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

const BASE_URL = "http://localhost:8080/api/ingredients";

@Injectable({
  providedIn: 'root'
})
export class IngredientService {


  constructor(private httpClient: HttpClient) { }

  getIngredients() {
    return this.httpClient.get<string[]>(BASE_URL);
  }
}
