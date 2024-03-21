// import { IconInterface } from '../../interface/common';

interface ClickMethod {
  styleString: string;
  clickMethod: (index: number) => void;
  index: number;
}

export default function CloseIcon({
  styleString,
  clickMethod,
  index,
}: ClickMethod) {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      fill="none"
      viewBox="0 0 24 24"
      strokeWidth={1.5}
      stroke="currentColor"
      className={`${styleString}`}
      onClick={() => clickMethod(index)}>
      <path
        strokeLinecap="round"
        strokeLinejoin="round"
        d="M6 18L18 6M6 6l12 12"
      />
    </svg>
  );
}
