export interface Book {
  bookId: number;
  title: string;
  author: string;
  publisher: string;
}

export interface BookInfo extends Book {
  image: string;
}

export interface PredictBookInfo extends Book {
  coverImage: string;
  status: string;
  originalPrice: number;
  estimatedPrice: number;
}

export interface ScoreInfo {
  status: string;
  all: number[];
  back: number[];
  cover: number[];
  side: number[];
}

export interface DoughnutProps {
  styleString: string;
  label: string;
  chartData: number[];
}
