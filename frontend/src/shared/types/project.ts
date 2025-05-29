export interface Project {
  id: number;
  title: string;
  status: '예정' | '진행 중' | '완료' | '지연';
  isPinned: boolean;
  isMyTeam: boolean;
  issues: { id: number; title: string }[];
}