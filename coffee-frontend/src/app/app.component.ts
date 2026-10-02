import {Component, OnInit, inject} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CoffeeService, Result, Stock} from './coffee.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {
  private api = inject(CoffeeService);

  stock?: Stock;
  log: { time: string; message: string; ok: boolean }[] = [];
  refill = {water: 0, milk: 0, coffeeBeans: 0, cups: 0};

  recipes = [
    {id: 1, name: 'Espresso', water: 250, milk: 0, beans: 16, price: 4},
    {id: 2, name: 'Latte', water: 350, milk: 75, beans: 20, price: 7},
    {id: 3, name: 'Cappuccino', water: 200, milk: 100, beans: 12, price: 6},
  ];

  ngOnInit() {
    this.api.remaining().subscribe({
      next: (s) => (this.stock = s),
      error: () => this.push('Backend offline — inicie o Quarkus na porta 8080', false),
    });
  }

  buy(id: number) {
    this.api.buy(id).subscribe((r) => this.apply(r));
  }

  take() {
    this.api.take().subscribe((r) => this.apply(r));
  }

  fill() {
    this.api.fill(this.refill).subscribe((r) => this.apply(r));
    this.refill = {water: 0, milk: 0, coffeeBeans: 0, cups: 0};
  }

  private apply(r: Result) {
    this.stock = r.stock;
    this.push(r.message, !r.message.startsWith('Not enough'));
  }

  private push(message: string, ok: boolean) {
    this.log.unshift({time: new Date().toLocaleTimeString(), message, ok});
  }
}
