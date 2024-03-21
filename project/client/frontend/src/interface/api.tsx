import { BookInfo, PredictBookInfo, ScoreInfo } from './predictResult';

export interface AuthRequset {
  dispatch: Function;
}

// predict

export interface APIResponse {
  code: number;
  message: string;
}

export interface TaResponse extends APIResponse {
  data: { bookInfo: BookInfo } | null;
}

export interface PredictResponse extends APIResponse {
  data: {
    predictBookInfo: PredictBookInfo;
    scInfo: ScoreInfo;
  } | null;
}

// history

export interface Book {
  id: number;
  price: string;
  status: string | null;
  title: string;
  url: string;
}

export interface HistoriesResponse {
  code: string;
  message: string;
  data: { history: Book[] };
}

export interface BooksInfo {
  bookId: number;
  title: string;
  author: string;
  publisher: string;
  coverImage: string;
  status: string;
  originalPrice: number;
  estimatedPrice: number;
}

export interface DetailResponse {
  code: string;
  message: string;
  data: {
    bookInfo: BooksInfo;
  };
}
