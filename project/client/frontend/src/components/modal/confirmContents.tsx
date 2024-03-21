import TrashCan from '../../assets/icons/trashIcon';

interface Content {
  content: string;
  okAction: () => void;
  cancelAction: () => void;
}

function ConfirmContents({ content, okAction, cancelAction }: Content) {
  return (
    <div className="h-full p-5 bg-BACKGROUND-50 rounded-2xl shadow-md flex flex-col justify-center items-center">
      <TrashCan styleString="w-[80px]" />
      <p className="text-xl text-gray-500 my-2">{content}</p>
      <div className="flex justify-center mt-3 w-[90%] ">
        <button
          type="button"
          className="rounded-md w-[25%] py-2 mx-2 text-BUTTON2-500"
          onClick={cancelAction}>
          취소
        </button>
        <button
          type="button"
          className="rounded-md w-[25%] py-2 mx-2 hover:bg-BUTTON1-900 bg-BUTTON1-500 text-BACKGROUND-50"
          onClick={okAction}>
          확인
        </button>
      </div>
    </div>
  );
}

export default ConfirmContents;
