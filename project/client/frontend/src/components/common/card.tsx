import { CardProps } from '../../interface/common';

function Card({ children, width, height }: CardProps) {
  return (
    <div className={`flex justify-center mt-8 ${height}`}>
      <div
        className={`${width} mb-3 rounded-2xl overflow-hidden shadow-2xl p-16`}>
        {children}
      </div>
    </div>
  );
}

export default Card;
