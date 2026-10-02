import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

export interface Stock { water: number; milk: number; coffeeBeans: number; cups: number; money: number; }
export interface Result { message: string; stock: Stock; }

@Injectable({ providedIn: 'root' })
export class CoffeeService {
  private http = inject(HttpClient);
  private api = 'http://localhost:8080/api/machine';

  remaining() { return this.http.get<Stock>(`${this.api}/remaining`); }
  buy(type: number) { return this.http.post<Result>(`${this.api}/buy/${type}`, {}); }
  fill(body: Omit<Stock, 'money'>) { return this.http.post<Result>(`${this.api}/fill`, body); }
  take() { return this.http.post<Result>(`${this.api}/take`, {}); }
}
