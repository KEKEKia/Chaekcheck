import { ReactNode } from 'react';

export interface IconInterface {
  styleString: string;
}

export interface TrashcanIconInterface {
  styleString: string;
  action: () => void;
}

export interface CardProps {
  children: ReactNode;
  width: string;
  height: string;
}

export interface BtnInfo {
  width: string;
  height: string;
  defaultColor: string;
  selectedColor: string;
  fontColor: string;
  action: () => void;
  children: ReactNode;
}
