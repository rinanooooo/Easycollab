// 상태별 아이콘 매핑
export const statusIconMap: Record<string, string> = {
  예정: '🕓',
  진행중: '🔧',
  지연: '⏳',
  긴급: '🔥',
  주의: '⚠️',
  완료: '✅',
};

// 상태별 색상 매핑
export const statusColorMap: Record<string, string> = {
  예정: '#9ca3af',
  진행중: '#1e88e5',
  지연: '#8e24aa',
  긴급: '#e53935',
  주의: '#fb8c00',
  완료: '#2e7d32',
};
